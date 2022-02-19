package compiler.construction;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;



public class CompilerConstruction {

    
    public static void main(String[] args) throws IOException {
    LexicalAnalyzer la = new LexicalAnalyzer();
    //String input = "str ab = \"adadadadadad\"";
    Path path = Paths.get("input.txt");
    int linecount = 1;
    List<String> input = Files.readAllLines(path);
    Pattern alphabet = Pattern.compile("[a-zA-Z]");
    Pattern digits = Pattern.compile("[0-9]");
    boolean isLineComment = false;
    boolean isMultiLineComment = false;
    ArrayList<Token> ts = new ArrayList();
    ArrayList<String> words = new ArrayList();
    ArrayList<String> line = new ArrayList();
//        for (int i = 0; i < input.size(); i++) 
//        {
//            line = breakword(input.get(i));
//            for (int j = 0; j < line.size(); j++) 
//            {
//                words.add(line.get(j));
//            }
//
//        }
       // System.out.println(words);
        for (int j = 0; j < input.size(); j++) 
        {
            line = breakword(input.get(j));
            words.clear();
            for (int k = 0; k < line.size(); k++) {
                
                words.add(line.get(k));
            }
            isLineComment = false;
            System.out.println(words);
        linecount = j+1;
        for (int i = 0; i < words.size(); i++) 
        {
            
            int max = words.size() -1 ;
            String word = words.get(i);     // current word
            String word1 = "";              // previous word
            String word2 = "";              //next word
            if(i > 0)
            {word1 = words.get(i-1);}            
            if(i < max)
            {word2 = words.get(i+1);}  
            Matcher a = alphabet.matcher(word);
            Matcher d = digits.matcher(word);
            if (isLineComment == true) 
            {
                break;
            }
            else if (isMultiLineComment == true) 
            {
                if (word.charAt(0) == '*') 
                {
                    if (word2.charAt(0) == '/') 
                    {
                        isMultiLineComment = false;
                        i++;
                        continue;
                    }
                }
                else continue;
            }
             if (word.charAt(0) == '_') 
            {   
                if (la.isID(word) == true) 
                {
                    Token t = new Token();
                    t.CP = "Identifier";
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
                else
                {
                    Token t = new Token();
                    t.CP = "Invalid";
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
            }
            else if(word.charAt(0) == '\'')
                {
                    if (la.isCharConst(word) == true) 
                    {
                        if (word.charAt(word.length()-1) == '\'' ) {
                           Token t = new Token();
                            t.CP = "char constant";
                            t.VP = word.substring(1,word.length()-1);
                            t.line_no = linecount;
                            ts.add(t); 
                        }
                        else{
                            Token t = new Token();
                            t.CP = "Invalid";
                            t.VP = word;
                            t.line_no = linecount;
                            ts.add(t);
                        }
                    }
                    else
                    {
                        Token t = new Token();
                        t.CP = "Invalid";
                        t.VP = word;
                        t.line_no = linecount;
                        ts.add(t);  
                    }
                }
            else if(word.charAt(0) == '\"')
                {
                    if (la.isStrConst(word) == true) 
                    {
                        Token t = new Token();
                        t.CP = "string constant";
                        t.VP = word.substring(1,word.length()-1);
                        t.line_no = linecount;
                        ts.add(t);
                    }
                    else
                    {
                        Token t = new Token();
                        t.CP = "Invalid";
                        t.VP = word;
                        t.line_no = linecount;
                        ts.add(t);  
                    }
                }
            else if(a.find() == true)
            {
                if (la.isID(word) == true) 
                {
                    String s = la.is_keyword(word);
                    if (s == null || s == "" ) 
                    {
                    Token t = new Token();
                    t.CP = "Identifier";
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                    }
                    else{
                    Token t = new Token();
                    t.CP = s;
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                    }
                }
                else if (la.isID(word) == false) 
                {
                   Token t = new Token();
                    t.CP = "Invalid";
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t); 
                }
                
            }
            else if(d.find() == true)
            {
                if (la.isIntConst(word) == true) 
                {
                    Token t = new Token();
                    t.CP = "int constant";
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
                else if(la.isFloatConst(word) == true)
                {
                    Token t = new Token();
                    t.CP = "float constant";
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
                else{
                    Token t = new Token();
                    t.CP = "Invalid";
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
            }
            else if (la.isPunct(word.charAt(0))) 
            {
                Token t = new Token();
                t.CP = word;
                t.VP = word;
                t.line_no = linecount;
                ts.add(t);
            }
            else if (word.charAt(0) == '+' || word.charAt(0) == '-' ) 
            {                      
                Matcher d1 =digits.matcher(word1);
                Matcher d2 =digits.matcher(word2);
                if (d1.find() == true && d2.find() == true) 
                {
                  Token t = new Token();
                    t.CP = "+-";
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
                else if(d1.find() == false && d2.find() == true)
                {
                    if (la.isID(word1) == true) 
                    {
                        Token t = new Token();
                        t.CP = "+-";
                        t.VP = word;
                        t.line_no = linecount;
                        ts.add(t);
                    }
                    else if (la.isIntConst(word2) == true) 
                    {
                        Token t = new Token();
                        t.CP = "int constant";
                        t.VP = word + word2;
                        t.line_no = linecount;
                        ts.add(t);
                        i += 1;
                    }   
                    else if(la.isFloatConst(word2))
                    {
                        Token t = new Token();
                        t.CP = "float constant";
                        t.VP = word + word2;
                        t.line_no = linecount;
                        ts.add(t);
                        i += 1;
                    }   
                }else if(d1.find() == true && d2.find() == false)
                {
                        Token t = new Token();
                        t.CP = "+-";
                        t.VP = word;
                        t.line_no = linecount;
                        ts.add(t);
                }
                
                else
                {
                        Token t = new Token();
                        t.CP = "+-";
                        t.VP = word;
                        t.line_no = linecount;
                        ts.add(t);
                }
                
            }
            else if(word.charAt(0) == '>')
            {
                if (word2.charAt(0) == '=') 
                {
                    String opr = Character.toString(word.charAt(0)) + Character.toString(word2.charAt(0));
                    Token t = new Token();
                    t.CP = la.is_opr(opr);
                    t.VP = word + word2;
                    t.line_no = linecount;
                    ts.add(t);
                    i += 1; 
                }
                else
                {
                    Token t = new Token();
                    t.CP = la.is_opr(word);
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
            }
            else if(word.charAt(0) == '<')
            {
                if (word2.charAt(0) == '=') 
                {
                    String opr = Character.toString(word.charAt(0)) + Character.toString(word2.charAt(0));
                    Token t = new Token();
                    t.CP = la.is_opr(opr);
                    t.VP = word + word2;
                    t.line_no = linecount;
                    ts.add(t);
                    i += 1; 
                }
                else
                {
                    Token t = new Token();
                    t.CP = la.is_opr(word);
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
            }
            else if(word.charAt(0) == '!')
            {
                if (word2.charAt(0) == '=') 
                {
                    String opr = Character.toString(word.charAt(0)) + Character.toString(word2.charAt(0));
                    Token t = new Token();
                    t.CP = la.is_opr(opr);
                    t.VP = word + word2;
                    t.line_no = linecount;
                    ts.add(t);
                    i += 1; 
                }
                else
                {
                    Token t = new Token();
                    t.CP = la.is_opr(word);
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
            }
            else if(word.charAt(0) == '=')
            {
                if (word2.charAt(0) == '=') 
                {
                    String opr = Character.toString(word.charAt(0)) + Character.toString(word2.charAt(0));
                    Token t = new Token();
                    t.CP = la.is_opr(opr);
                    t.VP = word + word2;
                    t.line_no = linecount;
                    ts.add(t);
                    i += 1; 
                }
                else
                {
                    Token t = new Token();
                    t.CP = la.is_opr(word);
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
            }
            else if(word.charAt(0) == '*' || word.charAt(0) == '%')
                {
                    Token t = new Token();
                    t.CP = la.is_opr(word);
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t);
                }
            else if (word.charAt(0) == '/') 
            {
                switch (word2.charAt(0)) {
                    case '/':
                        isLineComment = true;
                        i++;
                        break;
                    case '*':
                        isMultiLineComment = true;
                        i++;
                        break;
                    default:
                        Token t = new Token();
                        t.CP = la.is_opr(word);
                        t.VP = word;
                        t.line_no = linecount;
                        ts.add(t);
                        break;
                }
            }
            else if(word.charAt(0) == '&' || word.charAt(0) == '|')
                {
                    if (word.charAt(1) == '&') 
                    {
                        Token t = new Token();
                        t.CP = la.is_opr(word);
                        t.VP = word;
                        t.line_no = linecount;
                        ts.add(t);
                    }
                    else if (word.charAt(1) == '|') 
                    {
                        Token t = new Token();
                        t.CP = la.is_opr(word);
                        t.VP = word;
                        t.line_no = linecount;
                        ts.add(t);
                    }
                }
            
             
            else
                {
                    Token t = new Token();
                    t.CP = "Invalid";
                    t.VP = word;
                    t.line_no = linecount;
                    ts.add(t); 
                }

        }}
        Token end = new Token("$", "$", linecount);
        ts.add(end);
        SyntaxAnalyzer sa = new SyntaxAnalyzer(ts);
        if (sa.Start() == true) 
        {
            if (sa.Validate() == true) 
            {
                System.out.println("Valid");
            }
            else System.out.println("Invalid1");
        }else  System.out.println("Invalid2");
        String ts_text = "Tokens: \n" + ts.toString().substring(2);
        try (PrintWriter out = new PrintWriter("output.txt")) {
         out.println(ts_text);
        }
//        System.out.println(ts_text);
        
    
    }
    public static ArrayList breakword(String word)
    {
        LexicalAnalyzer la = new LexicalAnalyzer();
        ArrayList words = new ArrayList();
        String test = "";
        Pattern digits = Pattern.compile("[0-9]");

        for (int i = 0; i < word.length(); i++) 
        {
            String oprcheck = la.is_opr(Character.toString(word.charAt(i)));
            if (test.startsWith("\'")) 
            {
                if (word.charAt(i) == '\'') 
                {
                    if (test.length() >= 3 && test.charAt(1) != '\\') 
                    {
                        words.add(test);
                        test = "";
                        test = test + word.charAt(i);
                    }
                    else if(test.length() >= 4)
                    {
                        words.add(test);
                        test = "";
                        test = test + word.charAt(i);
                    }
                    else if(test.length() == 3 && test.charAt(1) == '\\')
                    {
                        if (la.isCharConst(test) == true) 
                        {
                        test = test + word.charAt(i);
                        words.add(test);
                        test = "";
                        }
                        else
                        {
                        words.add(test);
                        test = "";
                        test = test + word.charAt(i);
                        }
                        
                    }
                    else if(test.length() == 2)
                    {
                        test = test + word.charAt(i);
                        words.add(test);
                        test = "";
                    }
                }
                else
                {
                    test = test + word.charAt(i);
                }
            }
            else if (test.startsWith("\""))
            {
                if (word.charAt(i) == '\"') 
                {
                    test = test + word.charAt(i);
                    words.add(test);
                    test = "";
                }
                else
                {
                    test = test + word.charAt(i);
                }
            }
            else if(word.charAt(i) == '\'' && !test.startsWith("\'"))
            {
                if (test != "") 
                {
                words.add(test); 
                test = "";
                }
                test = test + Character.toString(word.charAt(i));
            }
            else if(word.charAt(i) == '\"' && !test.startsWith("\""))
            {
                if (test != "") 
                {
                words.add(test);    
                test = "";
                }
                test = test + Character.toString(word.charAt(i));
            }
            //Check if character is operator
            else if (oprcheck != null) 
            {
                if (test != "") 
                {
                words.add(test);    
                }
                test = "";
                words.add(Character.toString(word.charAt(i)));
            }
            //Check if character is punctuator
            else if (la.isPunct(word.charAt(i)) == true || word.charAt(i) == '&'|| word.charAt(i) == '|' ) 
            {
                switch (word.charAt(i)) {
                    case '.':
                        {
                            Matcher d = digits.matcher(test);
                            if (d.find() == true)
                            {
                                test = test + ".";
                            }
                            else
                            {
                                words.add(test);
                                words.add(".");
                                test = "";
                            }
                            break;
                        }
                    case '&':
                        {
                            if (test.contains("&"))
                            {
                                test = test + "&";
                                words.add(test);
                                test = "";
                            }
                            else if(!test.isEmpty() && !test.contains("&"))
                            {
                                words.add(test);
                                test = "";
                                test = test + "&";
                            }
                            else
                            {
                                test = test + "&";
                            }
                            break;
                        }
                    case '|':
                        {
                            if (test.contains("|"))
                            {
                                test = test + "|";
                                words.add(test);
                                test = "";
                            }
                            else if(!test.isEmpty() && !test.contains("|"))
                            {
                                words.add(test);
                                test = "";
                                test = test + "|";
                            }
                            else
                            {
                                test = test + "|";
                            }
                            break;
                        }
                    default:
                        char c = word.charAt(i);
                        if (!test.isEmpty()) 
                        {
                        words.add(test);
                        }
                        words.add(Character.toString(c));
                        test = "";
                        break;
                }
            }                 
            //Check if character is space
            else if (word.charAt(i) == ' ') 
            {
                if (test != "") 
                {
                words.add(test);    
                }                
                test = "";
            }
            //Concatenate to word if character is not word breaker
            else
            {
                test = test + word.charAt(i);
            }

        }
            if (!test.isEmpty()) 
            {
                words.add(test);
            }
        return words;
    }
}
class LexicalAnalyzer
{
    public char[] pc = {';',',','.','[',']','(',')','{','}'};
    public String[][] kword = {{"int", "data type"},{"float", "data type"},{"bool","data type"},{"char", "data type"},{"str", "data type"},
        {"true","bool constants"}, {"false","bool constants"},{"void","void"},{"if","if"}, {"else","else"},{"for","for"},{"do","do"},{"while","while"},
        {"continue","continue & break"}, {"break", "continue and break"},{"return","return"},{"public","access modifiers"},
        {"private","access modifiers"},{"protected","access modifiers"},{"static","static"},{"new","new"},{"class","class"},{"extends", "extends"},
        {"this.","refer"},{"super.","refer"},{"override","override"},{"array","array"},{"list","list"},{"final","final"},{"abstract","abstract"},
        {"implements","implements"},{"interface","interface"},{"struct","struct"},{"main","main"}
    };
    public String[][] operators = {{"+","+-"},{"-","+-"},{"*","*/%"},{"/","*/%"},{"%","*/%"},{"=","="},
            {">","relational operators"},{"<","relational operators"},{"<=","relational operators"},{">=","relational operators"},
            {"!=","relational operators"},{"==","relational operators"},
            {"!","!"},{"&&","&&"},{"||","||"}
    };
    public String is_keyword(String word)
    {
        for (int i = 0; i < kword.length; i++) 
        {
            if (word.equals(kword[i][0])) 
            {
                return kword[i][1];
            }
        }
        return null;
    }
    public String is_opr(String word)
    {
        for (int i = 0; i < operators.length; i++) 
        {
            if (word.equals(operators[i][0])) 
            {
                return operators[i][1];
            }
        }
        return null;
    }
    public boolean isPunct(char ch)
    {
        for (int i = 0; i < pc.length; i++) {
            if (ch == pc[i]) 
            {
             return true;   
            }
        }
        return false;
    }
    public boolean isID(String word)
    {
       Pattern id = Pattern.compile("^[a-zA-Z]$|(^[_a-zA-Z]([_a-zA-Z0-9])*[a-zA-Z0-9]$)");
       Matcher matcher = id.matcher(word);
       boolean matchfound = matcher.find();
        if (matchfound == true) 
        {
            return true;
        }
        else return false;
    }
    public boolean isIntConst(String word)
    {
        //Pattern intc = Pattern.compile("^\\d+$");
        Pattern intc = Pattern.compile("^[+-]*[0-9]+$");
        Matcher matcher = intc.matcher(word);
        boolean matchfound = matcher.find();
        return matchfound == true;
    }
    public boolean isFloatConst(String word)
    {
        Pattern fltc = Pattern.compile("[+-]?([0-9]*[.])[0-9]+");
        Matcher matcher = fltc.matcher(word);
        boolean matchfound = matcher.find();
        return matchfound == true && this.isIntConst(word) == false;
    }
    public boolean isCharConst(String word)
    {
         Pattern strc = Pattern.compile("\'([^\"])\'");
        Matcher matcher = strc.matcher(word);
        boolean matchfound = matcher.find();
        if (matchfound == true) 
        {
            return true;
        }else return false;
    }
    public boolean isStrConst(String word){
        Pattern strc = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = strc.matcher(word);
        boolean matchfound = matcher.find();
        if (matchfound == true) 
        {
            return true;
        }else return false;

    }
    
}
class Token{
    String CP;
    String VP;
    int line_no;
    
    Token(){}
    Token(String classp, String valuep, int line)
    {
     this.CP = classp;
     this.VP = valuep;
     this.line_no = line;
    }
    
    @Override
    public String toString()
    {
      String s = "\nClass Part: " + this.CP + " Value Part: " + this.VP + " Line No: " + this.line_no;
      return s;
    }
}