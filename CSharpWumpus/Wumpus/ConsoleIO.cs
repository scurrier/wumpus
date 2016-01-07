using System;

namespace Wumpus
{
    public class ConsoleIO : IO
    {
        public void WriteLine(string data)
        {
            Console.WriteLine(data);
        }

        public void Prompt(string data)
        {
            Console.Write(data);
        }

        public char ReadChar()
        {
            char istr = (char)Console.Read();
            Console.Read();
            Console.Read();
            return istr;
        }

        public int readInt()
        {
            var readLine = Console.ReadLine() ?? "0";
            return int.Parse(readLine);
        }

        public void Continue()
        {
            Console.Read();
            Console.Read();
        }
    }
}