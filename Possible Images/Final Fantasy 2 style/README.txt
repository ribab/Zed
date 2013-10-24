These character sprites are 32x32 and is the type used in Final Fantasy, I edited it to be a 16x24 which is the type used in Link to the Past.

It actually might be easier to implement a Final Fantasy 2 style game, but Final Fantasy 2 is an RPG game and Link to the Past is an Adventure game. I'm not sure if Final Fantasy 2 sprites would feel natural in an action/adventure setting.

Final Fantasy 2 sprites are rendered to depict the character standing in the middle of a tile. This is achieved by shifting the sprite up a couple of pixels.

Idea to make the Character class be able to support both Link to the Past style and Final Fantasy 2 style:
Specify inside of the Character class the x and y location of the pixel within the sprite that would be used as the Character's top-left location and then draw the sprite shifted.