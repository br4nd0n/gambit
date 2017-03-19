# Gambit
A real-time Multi-Armed Bandit implementation that uses [Thompson Sampling](https://en.wikipedia.org/wiki/Thompson_sampling)

To run:

1. mvn clean install

2. mvn exec:java -Dexec.mainClass="mab.Gambit"

The implementation of Gambit here is a proof-of-concept for doing basic real time reinforcement learning using mult-arm bandit, using Thompson Sampling as the explore/exploit policy.
Running Gambit will print out in the console a series of steps & decisions where Gambit begins by trying different actions randomly, and as it gains confidence about the performance of a particular action, it will start to select that action more frequently, thus balancing the learning of performance by action with exploiting the highest performing action.

Contact: Brandon O'Brien [@hakczar](https://www.twitter.com/hakczar)
