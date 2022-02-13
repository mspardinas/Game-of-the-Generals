# Salpakan/Game of the Generals
Salpakan, or more commonly known as Game of the Generals, command prompt game developed using Java for my final project in CS 12, Computer Programming II, in UPD.

## Installation / Dependencies
```
Make sure to have the latest version of Java/JDK installed on your local machine.
```

## Running
Simply download the gameofgenerals_Pardinas.java file then compile and execute in your terminal.
```java
javac gameofgenerals_Pardinas.java
java gameofgenerals_Pardinas
```

## Input
- Initial input starts with exactly 42 lines describing the initial pieces of the board.
- Each line contains a piece abbreviation, followed by a space, then the tile on the board.
- White pieces are denoated by uppercased abbreviations, and black pieces are denoted by lowercased abbreviations.
- White pieces are entered first, then black pieces.

## Premade Input commands
- Display all pieces
```
display a
```
- Display white pieces
```
display w
```
- Display black pieces
```
display b
```
Empty tiles are displayed as '..', while opposing tiles are displayed as '##'

## Moving pieces
 - To move piece from tile 'a3' to tile 'a4'
 ```
 a3 a4
 ```
 
 ## Board
 ![alt text](https://github.com/mspardinas/Game-of-the-Generals/blob/master/readme-images/board.png)
 
 ## Pieces
 ![alt text](https://github.com/mspardinas/Game-of-the-Generals/blob/master/readme-images/pieces.png)
 
 ## Ranking of Pieces
```
Pieces                                   No. of Pieces  Function
General of the Army (Five Stars)                1       Eliminates any lower-ranking officer, the Private, and the Flag.
General (Four Stars)                            1       Eliminates any lower-ranking officer, the Private, and the Flag.
Lieutenant General (Three Stars)                1       Eliminates any lower-ranking officer, the Private, and the Flag.
Major General (Two Stars)                       1       Eliminates any lower-ranking officer, the Private, and the Flag.
Brigadier General (One Star)                    1       Eliminates any lower-ranking officer, the Private, and the Flag.
Colonel (Three Magdalo 7-Ray Suns)              1       Eliminates any lower-ranking officer, the Private, and the Flag.
Lieutenant Colonel (Two Magdalo 7-Ray Suns)     1       Eliminates any lower-ranking officer, the Private, and the Flag.
Major (One Magdalo 7-Ray Sun)                   1       Eliminates any lower-ranking officer, the Private, and the Flag.
Captain (Three Magdalo Triangles)               1       Eliminates any lower-ranking officer, the Private, and the Flag.
1st Lieutenant (Two Magdalo Triangles)          1       Eliminates any lower-ranking officer, the Private, and the Flag.
2nd Lieutenant (One Magdalo Triangle)           1       Eliminates the Sergeant, the Private, and the Flag.
Sergeant (Three Chevrons)                       1       Eliminates the Private, and the Flag.
Private (One Chevron)                           6       Eliminates the Spy, and the Flag.
Spy (Two Prying Eyes)                           2       Eliminates all officers from the rank of Sergeant up to 5-Star General and the Flag.
Flag (Philippine Flag)                          1       Eliminates the opposing Flag as long as it takes the aggressive action against the enemy Flag.
```

## Sample input and output
```
1L a3
2L b3
MJ c3
5G d3
4G e3
CP f3
2G g3
PV h3
SP i3
3G a2
SG b2
PV c2
1G e2
PV g2
PV h2
LC i2
PV b1
PV d1
CL e1
SP h1
FL i1
2g a6
pv b6
cp c6
pv d6
pv e6
cl g6
sp a7
1g b7
fl d7
mj e7
pv g7
sp h7
2l i7
1l a8
lc b8
4g c8
pv d8
3g f8
sg g8
5g h8
pv i8
W
display a
```

## Sample Game Instance
![alt text](https://github.com/mspardinas/Game-of-the-Generals/blob/master/readme-images/display_all.png)

## Playing
- Objective of the game is capturing the opposing team's flag, or moving one's flag to the opposing side of the board.
- Pieces may only move by one tile (either up, down, left, or right, if applicable).
- Players' turns are alternated each time a player moves his/her respective piece.
- [Standard Salpakan rules apply](http://www.geekyhobbies.com/game-of-the-generals-aka-salpakan-review-and-rules/).
- Game ends when a winning condition is reached at any point, printing the final state of the board with pieces revealed.
