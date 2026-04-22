const SCENE_CONTAINER = document.getElementById('sceneContainer')
const TILEMAP_IMAGE = document.getElementById('tilemap')
const SCENE_SIZE = 32
const TILEMAP_SIZE = 16
const ZOOM_LEVEL_MIN = 0.5
const ZOOM_LEVEL_MAX = 3
const ZOOM_FACTOR = 0.5

let tilemap = new Image()
let selectedIndex = -1
let currentZoom = 1
let loadedScene = null

async function readScene(scene) {
    const req = await fetch(`../main/assets/scenes/${scene}.json`)
    const json = await req.json()

    SCENE_CONTAINER.innerHTML = ''
    loadedScene = new Scene(json)
    return json
}

function renderScene() {
    SCENE_CONTAINER.innerHTML = ''

    const tiles = loadedScene.tiles
    for (let i=0; i<tiles.length; i++) {
        for (let j=0; j<tiles[i].length; j++) {
            SCENE_CONTAINER.appendChild(tiles[i][j].canvas)

            if(loadedScene.colliders[i][j] !== 0) {
                colorTile(tiles[i][j], 'rgba(255, 0, 0, 0.2)')
            }
        }
    }

    colorTile(loadedScene.spawnTile, 'rgba(0, 255, 0, 0.2)')
}

function colorTile(tile, color) {
    const canvas = tile.canvas
    const ctx = canvas.getContext('2d')

    ctx.fillStyle = color;
    ctx.fillRect(0, 0, canvas.width, canvas.height);
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
            loadedScene.selectedIndex = i
        }

        TILEMAP_IMAGE.appendChild(tile.canvas)
    }
}

function storeScene() {
    const jsonString = JSON.stringify(loadedScene.toJSON(), null, 2)
    const blob = new Blob([jsonString], { type: 'application/json' })
    const url = URL.createObjectURL(blob)

    const a = document.createElement('a')
    a.href = url
    a.download = 'scene.json'
    a.click()

    URL.revokeObjectURL(url)
}

async function init() {
    SCENE_CONTAINER.style.maxWidth = `${SCENE_SIZE * Tile.SIZE * Tile.SCALE}px`
    TILEMAP_IMAGE.style.maxWidth = `${TILEMAP_SIZE * TILEMAP_SIZE * Tile.SCALE}px`
    await loadTilemap()
    await loadScene('town/main')
}

function fillSceneData(name, json) {
    document.getElementById('sceneDataName').innerText = name
    document.getElementById('sceneDataSize').innerText = `${json.config.size}x${json.config.size}`
}

function zoom(direction) {
    if(direction === 0) {
        currentZoom = 1
    } else {
        currentZoom += direction * ZOOM_FACTOR
        currentZoom = Math.min(ZOOM_LEVEL_MAX, currentZoom)
        currentZoom = Math.max(ZOOM_LEVEL_MIN, currentZoom)
    }

    document.getElementById('btnZoomIn').toggleAttribute('disabled', currentZoom === ZOOM_LEVEL_MAX)
    document.getElementById('btnZoomOut').toggleAttribute('disabled', currentZoom === ZOOM_LEVEL_MIN)
    const container = document.getElementById('sceneContainer');
    container.style.transform = `scale(${currentZoom})`;
    container.style.transformOrigin = 'top left';
}

async function loadScene(name) {
    const json = await readScene(name)
    fillSceneData(name, json)
    zoom(0)
    renderScene()
}

init()