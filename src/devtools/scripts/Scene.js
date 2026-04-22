class Scene {
    #size = 0
    #spawn = [0, 0]
    #tiles = null
    #colliders = null

    get tiles() {
        return this.#tiles
    }

    get colliders() {
        return this.#colliders
    }

    get spawnTile() {
        return this.tiles[this.#size - this.#spawn[1]][this.#spawn[0]]
    }

    constructor(json) {
        this.#size = json.config.size
        this.#spawn = json.config.spawn

        this.#tiles = []
        this.#colliders = []
        for (let i=0; i<this.#size; i++) {
            this.#tiles[i] = []
            this.#colliders[i] = []
        }

        this.#loadTiles(json['tiles'])
    }

    #loadTiles(tiles) {
        for (let i=0; i<this.#size; i++) {
            for (let j=0; j<this.#size; j++) {
                let tile = new Tile(tiles['indices'][i + j * this.#size])
                tile.onclick = () => {
                    console.log(selectedIndex)
                    if(selectedIndex === -1) { return }
                    tile.changeIndex(selectedIndex)
                    renderScene()
                }

                this.#tiles[j][i] = tile
                this.#colliders[j][i] = tiles['solid'][i + j * this.#size]
            }
        }
    }

    toJSON() {
        return {
            "config" : {
                "size"  : this.#size,
                "spawn" : this.#spawn
            },
            "tiles" : {
                "indices" : this.tiles.flat().map(tile => tile.index),
                "solid"   : this.colliders.flat()
            },
            "entities" : [
                { "pos":  [14,  7], "type": "TALKER", "texture": "guard", "parameter": "DEFAULT" },
                { "pos":  [18,  7], "type": "TALKER", "texture": "guard", "parameter": "DEFAULT" },
                { "pos":  [0,  10], "type": "TALKER", "texture": "guard", "parameter": "DEFAULT" },
                { "pos":  [0,   9], "type": "TALKER", "texture": "guard", "parameter": "DEFAULT" },
                { "pos":  [18, 26], "type": "TALKER", "texture": "guard", "parameter": "DEFAULT" },
                { "pos":  [14, 26], "type": "TALKER", "texture": "guard", "parameter": "DEFAULT" },
                { "pos":  [1,  11], "type": "TALKER", "texture": "guard", "parameter": "DEFAULT" },
                { "pos":  [3,  20], "type": "TALKER", "texture": "guard", "parameter": "DEFAULT" }
            ],
            "trigger" : [
                { "pos":  [12, 11], "type": "OPEN_SHOP", "parameter": "SHOP_TOWN" }
            ]
        }
    }
}