using System;
using System.Collections.Generic;
using System.IO;

namespace Wumpus
{
    public class Program {
        static void Main(string[] args)
        {
            var game = new Game(new ConsoleIO());
            game.random = new Random(0);
            game.Play();
        }
    }
}