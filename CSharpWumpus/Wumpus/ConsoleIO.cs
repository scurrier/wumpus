using System;

namespace Wumpus
{
    public class ConsoleIO : IO
    {
        public override void WriteLine(string data)
        {
            Console.WriteLine(data);
        }

        public override void Prompt(string data)
        {
            Console.Write(data);
        }

        public override char ReadChar()
        {
            char istr = (char)Console.Read();
            Console.Read();
            Console.Read();
            return istr;
        }

        public override int readInt()
        {
            var readLine = Console.ReadLine() ?? "0";
            return int.Parse(readLine);
        }

        public override void Continue()
        {
            Console.Read();
            Console.Read();
        }
    }
}