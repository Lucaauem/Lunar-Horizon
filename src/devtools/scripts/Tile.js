class Tile {
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
        tileCanvas.width = tileWidth
        tileCanvas.height = tileHeight

        ctx.drawImage(tilemap, sx, sy, tileWidth, tileHeight, 0, 0, tileWidth, tileHeight)

        return tileCanvas
    }
}