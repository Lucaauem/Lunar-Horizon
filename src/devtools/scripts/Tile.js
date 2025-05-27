class Tile {
    static SCALE = 2.5
    static SIZE = 16
    #index = -1
    #canvas = null
    #onclick = null
    #modifier = 0b00000000

    constructor(index, modifier) {
        this.changeIndex(index, modifier)
    }

    changeIndex(index, modifier) {
        this.#index = index
        this.#modifier = modifier
        this.#canvas = this.#getTile(index)
        this.#canvas.onclick = this.#onclick
    }

    get canvas() {
        return this.#canvas
    }

    get modifier() {
        return this.#modifier
    }
    
    get index() {
        return this.#index
    }

    set onclick(func) {
        this.#canvas.onclick = func
        this.#onclick = func
    }

    toggleCollision() {
        this.#modifier = this.#modifier ^ 0b10000000
    }

    setRotations(times) {
        this.#modifier = this.#modifier & 0b11111100
        this.#modifier = this.#modifier | times
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

        // Add modifier formatting
        const rotations = this.modifier & 0b00000011
        tileCanvas.classList.add(`rotate-${rotations}`)
        if(this.#modifier & 0b10000000) {
            ctx.fillStyle = 'rgba(255, 0, 0, 0.2)';
            ctx.fillRect(0, 0, tileCanvas.width, tileCanvas.height);
        }

        return tileCanvas
    }
}