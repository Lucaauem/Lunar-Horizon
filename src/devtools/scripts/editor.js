const SCENE_CONTAINER = document.getElementById('sceneContainer')
const TILEMAP_IMAGE = document.getElementById('tilemap')
const SCENE_SIZE = 32
const TILEMAP_SIZE = 16
const ZOOM_LEVEL_MIN = 0.5
const ZOOM_LEVEL_MAX = 3
const ZOOM_FACTOR = 0.5
const DEFAULT_SCENE = 'town'

let tilemap = new Image()
let selectedIndex = -1
let tiles = []
let selectedModifier = 0b00000000
let currentZoom = 1

async function readScene(scene) {
    const req = await fetch(`../main/assets/scenes/${scene}/tiles.bin`)
    const buffer = await req.arrayBuffer()
    const bytes = new Uint8Array(buffer)
    
    generateScene(bytes)
}

function generateScene(bytes) {
    SCENE_CONTAINER.innerHTML = ''

    for(let i=0; i<bytes.length; i+=2) {
        const tile = new Tile(bytes[i], bytes[i + 1])

        tile.onclick = () => {
            if(selectedIndex == -1) { return }
            
            tile.changeIndex(selectedIndex, selectedModifier)
            renderScene()
        }

        tiles.push(tile)
        SCENE_CONTAINER.appendChild(tile.canvas)
    }
}

function renderScene() {
    SCENE_CONTAINER.innerHTML = ''

    tiles.forEach(tile => {
        SCENE_CONTAINER.appendChild(tile.canvas)
    })
}

function setCollision(e) {
    if(e.target.checked) {
        selectedModifier = selectedModifier | 0b10000000
    } else {
        selectedModifier = selectedModifier & 0b01111111
    }
}

function setRotations(e) {
    if((e.target.value < 0) || (e.target.value > 3)) {
        e.target.value = selectedModifier & 0b00000011
        return
    }

    selectedModifier = selectedModifier & 0b11111100
    selectedModifier = selectedModifier | e.target.value
}

async function loadTilemap() {
    tilemap.src = '../main/assets/textures/tiles.png'
    await tilemap.decode()

    for(let i=0; i<TILEMAP_SIZE * TILEMAP_SIZE; i++) {
        const tile = new Tile(i)

        tile.onclick = () => {
            selectedIndex = i
            tile.changeIndex(i)
            document.getElementById('tilePreview').innerHTML = ''
            document.getElementById('tilePreview').appendChild(tile.canvas)
        }

        TILEMAP_IMAGE.appendChild(tile.canvas)
    }
}

function generateBitfield() {
    const buffer = new Uint8Array(tiles.length * 2)

    tiles.forEach((tile, i) => {
        buffer[i * 2] = tile.index
        buffer[i * 2 + 1] = tile.modifier
    })

    const blob = new Blob([buffer], { type: 'application/octet-stream' })
    const url = URL.createObjectURL(blob)
    
    const a = document.createElement('a')
    a.href = url
    a.download = 'tiles.bin'
    a.click()
    
    URL.revokeObjectURL(url)
}

function loadEmptyScene() {
    const bytes = new Uint8Array(SCENE_SIZE * SCENE_SIZE * 2)
    
    for(let i=0; i<bytes.length; i++) {
        bytes[i] = 0
    }

    generateScene(bytes)
}

async function init() {
    SCENE_CONTAINER.style.maxWidth = `${SCENE_SIZE * Tile.SIZE * Tile.SCALE}px`
    TILEMAP_IMAGE.style.maxWidth = `${TILEMAP_SIZE * TILEMAP_SIZE * Tile.SCALE}px`
    await loadTilemap()
    loadEmptyScene()
}

function fillSceneData(name) {
    document.getElementById('sceneDataName').innerText = name
    document.getElementById('sceneDataSize').innerText = `${SCENE_SIZE}x${SCENE_SIZE}`
}

function zoom(direction) {
    if(direction == 0) {
        currentZoom = 1
    } else {
        currentZoom += direction * ZOOM_FACTOR
        currentZoom = Math.min(ZOOM_LEVEL_MAX, currentZoom)
        currentZoom = Math.max(ZOOM_LEVEL_MIN, currentZoom)
    }

    document.getElementById('btnZoomIn').toggleAttribute('disabled', currentZoom == ZOOM_LEVEL_MAX)
    document.getElementById('btnZoomOut').toggleAttribute('disabled', currentZoom == ZOOM_LEVEL_MIN)
    const container = document.getElementById('sceneContainer');
    container.style.transform = `scale(${currentZoom})`;
    container.style.transformOrigin = 'top left';
}

async function loadScene(name) {
    await readScene(name)
    fillSceneData(name)
    zoom(0)
}

init()