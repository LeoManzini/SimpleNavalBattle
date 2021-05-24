# SimpleNavalBattle
A little sea battle game with what I know about Java.

## Technologies
- Java SE 1.8
- Maven 3.1

## Description
It consists of a matrix used as field with X by Y coordinates, at each position you can have a ship or nothing there, shot and you will discover. 
But remember who sink all the enemy ships wins!

The game runs at the terminal, you just need to have installed a JVM to read the Java code, and with the follow line you start a match:

``` java
java -jar NavalBattle.jar
```

Just remember, to run the game be sure that at your terminal session you are at the same folder that your executable file is.

![executable](https://user-images.githubusercontent.com/39606289/119280995-3502c180-bc0a-11eb-846b-def834a7a1f9.png)

## Technology inside this game

I used Java Object Oriented programming to develop this file, each layer of the game is separated into a package, and each class has just the methods
related to their function, being the classes:

- Board;
- Game;
- Player;
- Ships.

Related to the entity package, objects being the game's entities, with all their methods and characteristics.

- GameExecutor.

Related to the executor package, is an abstract class responsive by execute the game, as the name says, have all the methods to run and validate the game conditions.

- ShipPositions

Related to the utils package, this enum store the board conditions, being each value one characteristic to the programm load and show to the user.

- MainGame

This class is related to the main class, It's responsible to call the executor, passing the right objects to the program runs fine and without broke.

![project_structure](https://user-images.githubusercontent.com/39606289/119280895-d2112a80-bc09-11eb-9fe8-c9c1b738fa68.png)

I used to Maven to manage the project dependencies.

Logical reasoning and good programming practices were artifices that I used the most, because dealing with errors, imagining possible unpleasant circumstances 
in which the player might find himself during the execution of the game, as a monstrous error message without treatment, 
to make the user experience the coolest I could do, make me a better programmer and the user experience the best possible too. 

Was inserted more users interactions, like if he really want to start a game, if when the first one ends he want to leave or play again, and a lot of new improves too.

![start_screen](https://user-images.githubusercontent.com/39606289/119281639-d1c65e80-bc0c-11eb-8a09-f569f04d4cfc.png)

![boards](https://user-images.githubusercontent.com/39606289/119281679-f28eb400-bc0c-11eb-8d37-3af4c8151796.png)

The game version is currently in version: 2.0,

Because a refactor at the original version was need, but both are functional, the executable file available were compiled with the newest game version.

You can found the executable file at the folder "Executable" at this repository.

I hope you enjoy my work!
