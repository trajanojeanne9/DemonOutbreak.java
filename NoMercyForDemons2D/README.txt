NO MERCY FOR DEMONS (2D)  -  Java Swing, no libraries needed

Run:      javac *.java
          java NoMercyForDemons

Controls: Left/Right or A/D = walk     Up/Down/Left/Right or mouse = choose
          ENTER / SPACE / Z or click = confirm     1-6 = pick an option
          F11 = fullscreen on/off      ESC = leave fullscreen

Files
  GameImages.java   all backgrounds + button/text-box images
  GameSprites.java  all heroes, demons, NPCs, loot, potions, boss materials
  GamePanel.java    window, game loop, HUD, fades      DialogBox.java  the Pokemon-style box
  GameState.java    HP, role, potion, loot, coins, side-quest choices, chances
  Scene.java / WalkScene.java / Player.java   engine
  TitleScene, InfoScene, RoleScene, ForestScene, DungeonScene, DungeonHallScene,
  TreasureScene, TownScene, SideQuestScene, BossScene, FateScene   the story

Your own art (optional): put PNGs in  assets/backgrounds/<key>.png  and  assets/sprites/<key>.png
(keys are listed at the top of GameImages.java and GameSprites.java).
