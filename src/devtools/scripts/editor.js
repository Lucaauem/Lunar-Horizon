const SCENE_CONTAINER = document.getElementById('sceneContainer')
const TILEMAP_IMAGE = document.getElementById('tilemap')
const SCENE_SIZE = 8
const TILEMAP_SIZE = 16
const TILE_SIZE = 16

let tilemap = new Image()
let selectedIndex = -1
let tiles = []

async function readScene(scene) {
    const req = await fetch(`../main/assets/scenes/${scene}/tiles.bin`)
    const buffer = await req.arrayBuffer()
    const bytes = new Uint8Array(buffer)

    for(let i=0; i<bytes.length; i+=2) {
        const tile = new Tile(bytes[i])

        tile.onclick = () => {
            if(selectedIndex == -1) { return }
            
            tile.changeIndex(selectedIndex)
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

function generateBitmap() {
    const buffer = new Uint8Array(tiles.length * 2)

    tiles.forEach((tile, i) => {
        buffer[i * 2] = tile.index
        buffer[i * 2 + 1] = 0x00
    })

    const blob = new Blob([buffer], { type: 'application/octet-stream' })
    const url = URL.createObjectURL(blob)
    
    const a = document.createElement('a')
    a.href = url
    a.download = 'tiles.bin'
    a.click()
    
    URL.revokeObjectURL(url)
}

async function init() {
    SCENE_CONTAINER.style.maxWidth = `${SCENE_SIZE * TILE_SIZE}px`
    TILEMAP_IMAGE.style.maxWidth = `${TILEMAP_SIZE * TILEMAP_SIZE}px`
    await loadTilemap()
    await readScene('town')
}

init()