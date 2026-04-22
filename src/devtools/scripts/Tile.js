class Tile {
    static SCALE = 2.5
    static SIZE = 16

    #index = -1
    #canvas = null
    #onclick = null

    constructor(index) {
        this.changeIndex(index)
    }

    changeIndex(index) {
        this.#index = index
        this.#canvas = this.#getTile(index)
        this.#canvas.onclick = this.#onclick
    }

    get canvas() {
        return this.#canvas
    }

    get index() {
        return this.#index
    }

    set onclick(func) {
        this.#canvas.onclick = func
        this.#onclick = func
    }

    #getTile(index) {
        const tileCount = TILEMAP_SIZE
        const tileWidth = tilemap.width / tileCount
        const tileHeight = tilemap.height / tileCount

        const x = index % tileCount
        const y = Math.floor(index / tileCount)
        const sx = x * tileWidth
        const sy = y * tileHeight

        const tileCanvas = document.createElement('canvas')
        const ctx = tileCanvas.getContext('2d')
        tileCanvas.width = tileWidth * Tile.SCALE
        tileCanvas.height = tileHeight * Tile.SCALE
        
        ctx.imageSmoothingEnabled = false
        ctx.drawImage(tilemap, sx, sy, tileWidth, tileHeight, 0, 0, tileWidth * Tile.SCALE, tileHeight * Tile.SCALE)

        return tileCanvas
    }
}