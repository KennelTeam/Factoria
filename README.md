# Factoria-App

Factoria is a game where you have to find prime dividers of a given number

### Game process

During the game you get a big number and on each iteration you have three primes to choose (only one is correct).

If you choose correct one, the big number is divided by your choice and you get another three primes.

The game continues until you get to 1.

## Modes of game

### SinglePlayer

While you are playing stats are collected (such time you've spent) and after game finishes you can see them

You have progress bar on the top (how many dividers you've already found)

Each second your score decreases and after each correct answer increases so hurry up!

### MultiPLayer

You compete with other player.

You have to start [server](https://github.com/KennelTeam/Factoria-Server) at your local network and connect to it from both phones. Then one user creates a room and the other one joins it.

At the top you will see 2 progress bars - yours and your opponent's.

## How it is made

App is written in pure Kotlin and uses android navigation component.

Interaction with server is made using Java Sockets and Kotlin Coroutines.

Other parts of app receive information from communication module by observing MutableLiveData where HashMap is stored.

We have spent a lot of time to establish communication with server and are not very confident about correctness of our solution.
If you have any ideas, we will be glad to hear!

## Problems

- App doesn't start if phone isn't connected to internet
- App closes after ~minute if it can't connect to server so you can't play even single-player mode

## Ways to improve

- Make primes smaller so user has real chances to calculate (not just randomly clicking)
- Balance game so users have more motivation of calculating rather than randomly clicking
