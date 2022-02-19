package compiler.construction;
import java.util.ArrayList;

public class SyntaxAnalyzer 
{
   ArrayList<Token> tokens = new ArrayList();
   String type = null;
   String name = null;
   String CM = null;
   String AM = null;
   String Classname = null;
   boolean Final = false;
   ArrayList parent = new ArrayList();
   SemanticAnalyzer sm = new SemanticAnalyzer();
   SyntaxAnalyzer() {}
   SyntaxAnalyzer(ArrayList<Token> tk)
   {
       this.tokens = tk;
   }
   int i=0;
   int i1 = 0;
   int i2 =0;
   boolean Validate()
   {
       if (tokens.get(i).CP.equals("$")) 
       {
           return true;
       }
       i1 = i+1; 
       i2 = i-1; 
       System.out.println(i);
       System.out.println(tokens.get(i2).VP);
       System.out.println(tokens.get(i).VP);
       System.out.println(tokens.get(i1).VP);
       return false;
    
   }

    boolean Start()
    {
        if (tokens.get(i).CP.equals("access modifiers") ||tokens.get(i).CP.equals("static") ||tokens.get(i).CP.equals("final") 
            || tokens.get(i).CP.equals("class")|| tokens.get(i).CP.equals("interface") ||tokens.get(i).CP.equals("struct")) 
        {
                if(mod() == true)
                {
                    if (tokens.get(i).CP.equals("class")) 
                    {
                        type = tokens.get(i).CP;
                        i++;
                        System.out.println(i);
                        if (tokens.get(i).CP.equals("Identifier")) 
                        {
                            name = tokens.get(i).VP;
                            Classname = name;
                            i++;
                            System.out.println(i);
                            if (inh()== true) 
                            {
                                if (!sm.insertMT(name, type, CM, parent)) 
                                {
                                    System.out.println("Redeclaration Error");
                                }
                                System.out.println(sm.maintable);
                                parent = null;
                                if (tokens.get(i).CP.equals("{")) 
                                {
                                    i++;
                                    System.out.println(i);
                                    if (CB() == true) 
                                    {
                                        if (tokens.get(i).VP.equals("public")) 
                                        {
                                            AM = "public";
                                            i++;
                                            if (tokens.get(i).CP.equals("static")) 
                                            {
                                                CM = "static";
                                             i++;
                                                if (tokens.get(i).CP.equals("void")) 
                                                {   
                                                type = "void";
                                                i++;
                                                if (tokens.get(i).CP.equals("main")) 
                                                {
                                                name = "main";
                                                    if (!sm.insertCT(name, type, AM, Final, CM, Classname)) 
                                                    {
                                                        System.out.println("Redeclaration Error");
                                                    }
                                                 i++;
                                                    if (tokens.get(i).CP.equals("(")) 
                                                    {
                                                     i++;
                                                        if (tokens.get(i).CP.equals(")")) 
                                                        {
                                                            i++;
                                                            if (tokens.get(i).CP.equals("{")) 
                                                            {
                                                            sm.createScope();   
                                                             i++;
                                                                if (MST() == true) 
                                                                {
                                                                    if (tokens.get(i).CP.equals("}")) 
                                                                    {   sm.destroyScope();
                                                                        i++;
                                                                        if (CB()==true) 
                                                                        {
                                                                            if (tokens.get(i).CP.equals("}")) 
                                                                            {
                                                                                i++;
                                                                                if (defs() == true ||tokens.get(i).CP.equals("$") ) 
                                                                                {
                                                                                    System.out.println(i);
                                                                                    System.out.println(tokens.size());
                                                                                  return true;    
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                int i1 = i+1;
                int i2 = i-1;
                System.out.println(i);
                System.out.println(tokens.get(i2).VP);
                System.out.println(tokens.get(i).VP);
                System.out.println(tokens.get(i1).VP);

        }return false;
    }
    
    boolean defs()
    {
        if (tokens.get(i).CP.equals("access modifiers") ||tokens.get(i).CP.equals("static") ||tokens.get(i).CP.equals("final") 
            || tokens.get(i).CP.equals("class")|| tokens.get(i).CP.equals("interface") ||tokens.get(i).CP.equals("struct")) 
        {
            if (mod() == true) 
            {
                if (static1() == true) 
                {
                    if (defs1() == true) 
                    {
                        if (defs() == true) 
                        {
                            return true;
                        }else return true;
                    }
                }
            }
            else if (struct_def() == true) 
            {
                return true;
            }
        }
        return false;    
    }
    boolean mod()
    {
        if (tokens.get(i).CP.equals("access modifiers") ||tokens.get(i).CP.equals("static") ||tokens.get(i).CP.equals("final") 
            || tokens.get(i).CP.equals("class")|| tokens.get(i).CP.equals("Identifier")|| tokens.get(i).CP.equals("data type") 
            || tokens.get(i).CP.equals("interface") || tokens.get(i).CP.equals("void")) 
        {
            if (tokens.get(i).CP.equals("access modifiers")) 
            {
                CM = tokens.get(i).VP;
                i++;
                return true;
            }else return true;
        }
        return false;
    }
    boolean inh()
    {
        if (tokens.get(i).CP.equals("extends") || tokens.get(i).CP.equals("implements") || tokens.get(i).CP.equals("{")) {
            if (tokens.get(i).CP.equals("extends")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    parent.add(tokens.get(i).VP);
                    i++;
                    if (inh2() == true) 
                    {
                        return true;
                    }
                }
            }else if (inh2() == true) 
            {
                return true;
            }else return true;
        }return false;
    }
    boolean inh2()
    {
        if (tokens.get(i).CP.equals("implements") || tokens.get(i).CP.equals("{")) 
        {
            if (tokens.get(i).CP.equals("implements")) 
            {
             i++;
                if (tokens.get(i).CP.equals("Identifier"))
                {
                parent.add(tokens.get(i).VP);
                i++;
                    if (inh3() == true) 
                    {
                        return true;
                    }
                }
            }else return true;
        }return false;
    }
    boolean inh3()
    {
        if (tokens.get(i).CP.equals("{") || tokens.get(i).CP.equals(",")) 
        {
            if (tokens.get(i).CP.equals(",")) 
            {
            i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                parent.add(tokens.get(i).VP);
                i++;
                    if (inh3() == true) 
                    {
                        return true;
                    }
                }
            }else return true;
        }return false;
    }
    boolean CB()
    {
        if (tokens.get(i).CP.equals("access modifiers") ||tokens.get(i).CP.equals("static") ||tokens.get(i).CP.equals("final") 
            || tokens.get(i).CP.equals("Identifier")|| tokens.get(i).CP.equals("data type") || tokens.get(i).CP.equals("void")) 
        {
            if ("main".equals(tokens.get(i+3).CP)) 
            {      
                return true;
            }
            if (mod() == true) 
            {
                if (static1() == true) 
                {
                    if (final1() == true) 
                    {
                        if (CB1() == true) 
                        {
                            if (CB() == true) 
                            {
                               return true;  
                            }
                        }
                    }
                }
            }
        }
        else if (tokens.get(i).CP.equals("}")) 
        {
            return true;                       
        }
        
        return false;
    }
    boolean CB1()
    {
        if (tokens.get(i).CP.equals("Identifier")|| tokens.get(i).CP.equals("data type") || tokens.get(i).CP.equals("void")) 
        {
            if (tokens.get(i).CP.equals("data type"))
            {
                type = tokens.get(i).VP;
            i++;
                if (CB2() == true) 
                {
                return true;    
                }
            }else if (tokens.get(i).CP.equals("Identifier")) 
            {
                type = tokens.get(i).VP;
            i++;
                if (tokens.get(i).CP.equals("[")) 
                {    
                i++;
                    if (tokens.get(i).CP.equals("]")) 
                    {
                        type = type ;
                        i++;
                        if (array1() == true) 
                        {
                            if (tokens.get(i).CP.equals("Identifier")) 
                            {
                                name = tokens.get(i).VP;
                            i++;
                                if (tokens.get(i).CP.equals("(")) 
                                {
                                    i++;
                                    if (para() == true) 
                                    {
                                        if (tokens.get(i).CP.equals(")")) 
                                        {
                                            if (!sm.insertCT(name, type, AM, Final, CM, Classname)) 
                                            {
                                                System.out.println("Redeclaration Error");
                                            }
                                            i++;
                                            if (tokens.get(i).CP.equals("{")) 
                                            {
                                                sm.createScope();
                                                i++;
                                                if (MST() == true) 
                                                {
                                                    if (tokens.get(i).CP.equals("}")) 
                                                    {
                                                       sm.destroyScope();
                                                        i++;
                                                        return true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
    
                        }
    
                    }
                }else
                {
                    if (tokens.get(i).CP.equals("Identifier")) 
                    {
                        name = tokens.get(i).VP;
                        i++;
                        if (tokens.get(i).CP.equals("(")) 
                        {
                            i++;
                            if (para() == true) 
                            {
                                if (tokens.get(i).CP.equals(")")) 
                                {
                                    i++;
                                    if (tokens.get(i).CP.equals("{")) 
                                    {
                                        sm.createScope();
                                        i++;
                                        if (MST() == true) 
                                        {
                                            if (tokens.get(i).CP.equals("}")) 
                                            {
                                                sm.destroyScope();
                                                i++;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }else if (tokens.get(i).CP.equals("void")) 
            {
                type = "void";
            i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    name = tokens.get(i).VP;
                i++;
                    if (tokens.get(i).CP.equals("(")) 
                    {
                    i++;
                        if (para() == true) 
                        {
                            if (tokens.get(i).CP.equals(")")) 
                            {
                                if (!sm.insertCT(name, type, AM, Final, CM, Classname)) 
                                {
                                    System.out.println("Redeclaration Error");
                                }
                            i++;
                                if (tokens.get(i).CP.equals("{")) 
                                {
                                    sm.createScope();
                                i++;       
                                    if (MST() == true) 
                                    {
                                        if(tokens.get(i).CP.equals("}"))
                                        {
                                            sm.destroyScope();
                                            i++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }return false;
    }
    boolean CB2()
    {
        if (tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("[")) 
        {
            if (tokens.get(i).CP.equals("Identifier")) 
            {
                name = tokens.get(i).VP;
            i++;
                if (CB3() == true) 
                {
                    return true;
                }
            }else if (tokens.get(i).CP.equals("[")) 
            {
                i++;
                if (tokens.get(i).CP.equals("]")) 
                {
                    type = type;
                i++;
                    if (array1() == true) 
                    {
                        if (tokens.get(i).CP.equals("Identifier")) 
                        {
                            name = tokens.get(i).VP;
                        i++;
                            if (tokens.get(i).CP.equals("(")) 
                            {
                            i++;
                                if (para() == true) 
                                {
                                    if (tokens.get(i).CP.equals(")")) 
                                    {
                                        if (!sm.insertCT(name, type + "[]", AM, Final, CM, Classname)) 
                                        {
                                            System.out.println("Redeclaration Error");
                                        }
                                    i++;
                                        if (tokens.get(i).CP.equals("{")) 
                                        {
                                            sm.createScope();
                                        i++;
                                            if (MST() == true) 
                                            {
                                                if (tokens.get(i).CP.equals("}")) 
                                                {
                                                    sm.destroyScope();
                                                i++;
                                                return true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }return false;
    }
    boolean CB3(){
        if (tokens.get(i).CP.equals(";") || tokens.get(i).CP.equals(",")||tokens.get(i).CP.equals("=")||tokens.get(i).CP.equals("(")) 
        {
            if (list() == true) 
            {
                return true;
            }else if (tokens.get(i).CP.equals("(")) 
            {
            i++;
                if (para() == true) 
                {
                    if (tokens.get(i).CP.equals(")")) 
                    {
                    i++;
                        if (!sm.insertCT(name, type, AM, Final, CM, Classname)) 
                        {
                            System.out.println("Redeclaration Error");
                        }
                        if (tokens.get(i).CP.equals("{")) 
                        {
                            sm.createScope();
                        i++;
                            if (MST() == true) 
                            {
                                if (tokens.get(i).CP.equals("}")) 
                                {
                                sm.destroyScope();
                                i++;
                                return true;
                                }
                            }
                        }
                    }
                }
            }
        }return false;
    }
    boolean MST()
    {
        if (tokens.get(i).CP.equals("refer") || tokens.get(i).CP.equals("Identifier") || tokens.get(i).CP.equals("while")
            || tokens.get(i).CP.equals("continue & break")||tokens.get(i).CP.equals("return")||tokens.get(i).CP.equals("if")
            ||tokens.get(i).CP.equals("for")||tokens.get(i).CP.equals("}")||tokens.get(i).CP.equals("data type")) 
        {
            if (SST() == true) 
            {
                if (MST() == true) 
                {
                    return true;
                }
            }
    
    return true;   
        }return false;
    }
    boolean SST()
    {
        if (tokens.get(i).CP.equals("refer") || tokens.get(i).CP.equals("Identifier") || tokens.get(i).CP.equals("while")
            || tokens.get(i).CP.equals("continue & break")||tokens.get(i).CP.equals("return")||tokens.get(i).CP.equals("if")
            ||tokens.get(i).CP.equals("for")||tokens.get(i).CP.equals("data type")) 
        {
            if (ref() == true) 
            {
                if (SST1()) 
                {
                    return true;
                }
            }else if (while_st() == true) 
            {
                return true;
            }
            else if (if_else_st() ==true) 
            {
                return true;
            }
            else if (for_st() == true) 
            {
                return true;
            }
            else if (obj_dec() == true) 
            {
                return true;
            }
            else if (tokens.get(i).CP.equals("continue & break"))
            {
                i++;
                if (tokens.get(i).CP.equals(";")) 
                {
                    i++;
                    return true;
                }    
            }
            else if (ret_st() == true) 
            {
                return true;
            }
            else if (declaration() == true) 
            {
                return true;
            }
        }return false;   
    }
    boolean SST1()
    {
        if (tokens.get(i).CP.equals("=")||tokens.get(i).CP.equals("else")||tokens.get(i).CP.equals("}")||tokens.get(i).CP.equals(";")) 
        {
            if (tokens.get(i).CP.equals("=")) 
            {
                i++;
                if (OE() == true) 
                {
                    if (tokens.get(i).CP.equals(";")) 
                    {
                        i++;
                        return true;
                    }
                }
            }else return true;
        }return false;    
    }
    boolean static1()
    {
        if (tokens.get(i).CP.equals("static") ||tokens.get(i).CP.equals("final") 
           || tokens.get(i).CP.equals("class")|| tokens.get(i).CP.equals("Identifier")|| tokens.get(i).CP.equals("data type") 
           || tokens.get(i).CP.equals("interface") || tokens.get(i).CP.equals("void")) 
        {
            if (tokens.get(i).CP.equals("static")) 
            {
                CM = "static";
                i++;
                return true;
            }else return true;
        }return false;
    }
    boolean final1(){
        if (tokens.get(i).CP.equals("final")|| tokens.get(i).CP.equals("class")|| tokens.get(i).CP.equals("Identifier")
           || tokens.get(i).CP.equals("data type")|| tokens.get(i).CP.equals("void")) 
        {
            if (tokens.get(i).CP.equals("final")) 
            {
                Final = true;
                i++;
                return true;
            }else return true;
        }return false;
    }
    boolean defs1()
    {
        if (tokens.get(i).CP.equals("final")||tokens.get(i).CP.equals("class")||tokens.get(i).CP.equals("interface")) 
        {
            if (final1() == true) 
            {
                if (tokens.get(i).CP.equals("class")) 
                {
                    type = "class";
                    i++;
                    if (tokens.get(i).CP.equals("Identifier")) 
                    {
                        name = tokens.get(i).VP;
                        Classname = name;
                        i++;
                        if (inh() == true) 
                        {
                            if (!sm.insertMT(name, type, CM, parent)) 
                                {
                                    System.out.println("Redeclaration Error");
                                }
                            parent = null;
                            if (tokens.get(i).CP.equals("{")) 
                            {
                                i++;
                                if (CB() == true) 
                                {
                                    if (tokens.get(i).CP.equals("}")) 
                                    {
                                        i++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else if (tokens.get(i).CP.equals("interface")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("{")) 
                    {
                        i++;
                        if (IB() == true) 
                        {
                            if (tokens.get(i).CP.equals("}")) 
                            {
                                i++;
                                return true;
                            }
                        }
                    }
                }
            }
        }return false;    
    }
    boolean struct_def(){
        if (tokens.get(i).CP.equals("struct")) 
        {
            if (tokens.get(i).CP.equals("struct")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("{")) 
                    {
                        i++;
                        if (declaration() == true) 
                        {
                            if (tokens.get(i).CP.equals("}")) 
                            {
                                i++;
                                return true;
                            }
                        }
                    }
                }
            }
        }return false;
    }
    boolean para()
    {
        if (tokens.get(i).CP.equals("data type")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals(")")) 
        {
            if (tokens.get(i).CP.equals("data type")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (para1() == true) 
                    {
                        return true;
                    }
                }
            }
            else if (tokens.get(i).CP.equals("Identifier")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (para1()== true) 
                    {
                        return true;
                    }
                }
            }
            else if (array() == true) 
            {
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (para1() == true) 
                    {
                        return true;
                    }                    
                }
            }
        return true;
        }return false;    
    }
    boolean para1()
    {
        if (tokens.get(i).CP.equals(",")||tokens.get(i).CP.equals("}")) 
        {
            if (tokens.get(i).CP.equals(",")) 
            {
                i++;
                if (para2() == true) 
                {
                    return true;
                }
            }else return true;
        }return false;    
    }
    boolean para2()
    {
        if (tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("data type")) 
        {
            if (tokens.get(i).CP.equals("data type")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (para1() == true) 
                    {
                        return true;
                    }
                }
            }
            else if (tokens.get(i).CP.equals("Identifier")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (para1() == true) 
                    {
                        return true;
                    }
                }
            }
            else if (array() == true) 
            {
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (para1() == true) 
                    {
                        return true;
                    }
                }
            }
        }return false;    
    }
    boolean list()
    {
        if (tokens.get(i).CP.equals(";")||tokens.get(i).CP.equals(",")||tokens.get(i).CP.equals("=")) 
        {
            if (tokens.get(i).CP.equals(";")) 
            {
                if (!sm.insertCT(name, type, AM, Final, CM, Classname)) 
                {
                    System.out.println("Redeclaration Error");
                }
                i++;
                return true;
            }
            else if (tokens.get(i).CP.equals(",")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (list() == true) 
                    {
                        return true;
                    }
                }
            }
            else if (tokens.get(i).CP.equals("=")) 
            {
                i++;
                if (list1() == true) 
                {
                    return true;
                }
            }
        }return false;    
    }
    boolean list1()
    {
        if (tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("char constant")) 
        {   
            if (constant() == true) 
            {
                if (list2()) 
                {
                    return true;
                }
            }
    }   else if (tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")||tokens.get(i).CP.equals("Identifier")
            ||tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("(")) 
            {   
             if (OE() == true) 
                {
                    if (tokens.get(i).CP.equals(";")) 
                    {
                        i++; 
                        return true;
                    }
                }
            }
        return false;
    }
    boolean list2()
    {
        if (tokens.get(i).CP.equals(";")||tokens.get(i).CP.equals(",")) 
        {
            if (tokens.get(i).CP.equals(";")) 
            {
                i++;
                return true;
            }
            else if (tokens.get(i).CP.equals(",")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (list() == true) 
                    {
                        return true;
                    }
                }
            }
        }return false;
    }
    boolean constant()
    {
        if (tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")
            ||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("char constant")) 
        {
            if (tokens.get(i).CP.equals("bool constants")) 
            {
                i++;
                return true;
            }
            else if (tokens.get(i).CP.equals("int constant")) 
            {
                i++;
                return true;
            }
            else if (tokens.get(i).CP.equals("float constant")) 
            {
                i++;
                return true;
            }
            else if (tokens.get(i).CP.equals("char constant")) 
            {
                i++;
                return true;
            }
            else if (tokens.get(i).CP.equals("string constant")) 
            {
                i++;
                return true;
            }
        }return false;
    }
    boolean array()
    {
        if (tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("data type")) 
        {
            if (tokens.get(i).CP.equals("data type")) 
            {
                i++;
                if (tokens.get(i).CP.equals("[")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("]")) 
                    {
                        i++;
                        if (array1() == true) 
                        {
                            return true;
                        }
                    }
                }
            }
            else if (tokens.get(i).CP.equals("Identifier")) 
            {
                i++;
                if (tokens.get(i).CP.equals("[")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("]")) 
                    {
                        i++;
                        if (array1() == true) 
                        {
                            return true;
                        }
                    }
                }
            }
        }return false;
    }
    boolean array1()
    {
        if (tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("[")) 
        {
            if (tokens.get(i).CP.equals("[")) 
            {
                i++;
                if (tokens.get(i).CP.equals("]")) 
                {
                    i++;
                    if (array1() == true) 
                    {
                        return true;
                    }
                }
            }else return true;
        }return false;
    }
    boolean ref()
    {
        if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")) 
        {
            if (ts() == true) 
            {
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    if (tokens.get(i+1).CP.equals("Identifier")) 
                    {
                        return false;
                    }
                    i++;
                    if (ref1() == true) 
                    {
                        return true;
                    }
                }
            }
        }return false;
    }
    boolean ref1()
    {
        if (tokens.get(i).CP.equals(".")||tokens.get(i).CP.equals("else")||tokens.get(i).CP.equals("}")
            ||tokens.get(i).CP.equals("*/%")||tokens.get(i).CP.equals("=")) 
        {
            if (tokens.get(i).CP.equals(".")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (ref11() ==true) 
                    {
                        return true;
                    }
                }
            }else return true;
        }return true;
    }
    boolean ref11()
    {
        if (tokens.get(i).CP.equals("refer")|| tokens.get(i).CP.equals("Identifier")) 
        {
            if (ref1() == true ) 
            {
                return true;
            }
            else if (ref2() == true) 
            {
                return true;
            }            
        }return false;
    }
    boolean ref2()
    {
        if (tokens.get(i).CP.equals("(")) 
        {
            if (tokens.get(i).CP.equals("(")) 
            {
                i++;
                if(args() == true)
                {
                    if (tokens.get(i).CP.equals(")")) 
                    {
                        i++;
                        if (ref1() == true) 
                        {
                            return true;
                        }
                    }
                }
            }
        }return true;
    }
    boolean while_st()
    {
        if (tokens.get(i).CP.equals("while")) 
        {
            if (tokens.get(i).CP.equals("while")) 
            {
                i++;
                if (tokens.get(i).CP.equals("(")) 
                {
                    i++;
                    if (OE() == true) 
                    {
                        if (tokens.get(i).CP.equals(")")) 
                        {
                            i++;
                            if (body() == true) 
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }return false;
    }
    boolean body()
    {
        if (tokens.get(i).CP.equals("{")||tokens.get(i).CP.equals(";")||tokens.get(i).CP.equals("refer") 
            ||tokens.get(i).CP.equals("Identifier") || tokens.get(i).CP.equals("while")| tokens.get(i).CP.equals("continue & break")
            ||tokens.get(i).CP.equals("return")||tokens.get(i).CP.equals("if")||tokens.get(i).CP.equals("for")) 
        {
            if (tokens.get(i).CP.equals(";")) 
            {
                i++;
                return true;
            }
            else if (SST() == true) 
            {
                return true;
            }
            else if (tokens.get(i).CP.equals("{")) 
            {
                i++;
                if (MST() == true) 
                {
                    if (tokens.get(i).CP.equals("}")) 
                    {
                        i++; 
                        return true;
                    }
                }
            }
        }return false;
    }
    boolean for_st()
    {
        if (tokens.get(i).CP.equals("for")) 
        {
            if (tokens.get(i).CP.equals("for")) 
            {
                i++;
                if (tokens.get(i).CP.equals("(")) 
                {
                    i++;
                    if (inst1() == true) 
                    {
                        if (tokens.get(i).CP.equals(";")) 
                        {
                            i++;
                            if (inst2() == true) 
                            {
                                if (tokens.get(i).CP.equals(";")) 
                                {
                                    i++;
                                    if (inst3() == true) 
                                    {
                                        if (tokens.get(i).CP.equals(")")) 
                                        {
                                            i++;
                                            if (body() == true) 
                                            {
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }return false;
    }
    boolean inst1()
    {
        if (tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("refer")
            ||tokens.get(i).CP.equals("data type")||tokens.get(i).CP.equals(";")) 
        {
            if (declaration() == true) 
            {
                return true;
            }
            else if (ref() == true) 
            {
                if (SST1() == true) 
                {
                    return true;
                }
            }
            else if (tokens.get(i).CP.equals(";")) 
            {
                return true;
            }
            else return true;
        }return false;
    }
    boolean inst2()
    {
        if (tokens.get(i).CP.equals(";")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("(")||tokens.get(i).CP.equals("refer")
            ||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("char constant")||tokens.get(i).CP.equals("bool constants")
            ||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")||tokens.get(i).CP.equals("string constant")) 
        {
            if (OE() == true) 
            {
                return true;
            }
            else return true;
        }return false;
    }
    boolean inst3()
    {
        if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")) 
        {
            if (ref() == true) 
            {
                if (tokens.get(i).CP.equals("=")) 
                {
                    i++;
                    if (inst4() == true) 
                    {
                        return true;
                    }
                }
            }
        }return false;
    }
    boolean inst4()
    {
        if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("char constant")
            ||tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")
            ||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("(")) 
        {
            if (tokens.get(i).CP.equals("Identifier")) 
            {
                if (OE() == true) 
                {
                 return true;    
                }
            }
            else if (ref() == true) 
            {
                if (tokens.get(i).CP.equals("+-")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("int constant")) 
                    {
                        i++;
                        return true;
                    }
                }
            }
        }return false;    
    }
    boolean if_else_st()
    {
        if (tokens.get(i).CP.equals("if")) 
        {
            if (tokens.get(i).CP.equals("if")) 
            {
                i++;
                if (tokens.get(i).CP.equals("(")) 
                {
                    i++;
                        if (OE() == true) 
                    {
                        if (tokens.get(i).CP.equals(")")) 
                        {
                            i++;
                            if (body() == true) 
                            {
                                if (else_st() == true) 
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }return false;
    }
    boolean else_st()
    {
        if (tokens.get(i).CP.equals("else")||tokens.get(i).CP.equals("}")||tokens.get(i).CP.equals(";")) 
        {
            if (tokens.get(i).CP.equals("else")) 
            {
                i++;
                if (body() == true) 
                {
                    return true;
                }
            }return true;
        }return false;
    }
    boolean obj_dec()
    {
        if (tokens.get(i).CP.equals("Identifier")) 
        {
            if (tokens.get(i).CP.equals("Identifier")) 
            {
                i++;
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("=")) 
                    {
                        i++;
                        if (tokens.get(i).CP.equals("new")) 
                        {
                            i++;
                            if (tokens.get(i).CP.equals("Identifier")) 
                            {
                                i++;
                                if (tokens.get(i).CP.equals("(")) 
                                {
                                    i++;
                                    if (args() == true) 
                                    {
                                        if (tokens.get(i).CP.equals(")")) 
                                        {
                                            i++;
                                            if (tokens.get(i).CP.equals(";")) 
                                            {
                                                i++;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }return false;    
    }
    boolean ret_st()
    {
        if (tokens.get(i).CP.equals("return")) 
        {
            if (tokens.get(i).CP.equals("return")) 
            {
                i++;
                if (ret_value() == true) 
                {   
                    if (tokens.get(i).CP.equals(";")) 
                    {
                        i++;
                        return true;
                    }
                }
            }
        }return false;
    }
    boolean ret_value()
    {
        if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("char constant")
            ||tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")
            ||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("(")) 
        {
            if (tokens.get(i).CP.equals("char constant")||tokens.get(i).CP.equals("bool constants")
            ||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")||tokens.get(i).CP.equals("string constant")) 
            {
                i++;
                return true;
            }
            else if (tokens.get(i).CP.equals("Identifier")) 
            {
                i++;
                return true;
            }
            else if(OE() == true)
            {
                return true;
            }
        }return false;
    }
    boolean declaration()
    {
        if (tokens.get(i).CP.equals("data type")||tokens.get(i).CP.equals("Identifier")) 
        {
            if (tokens.get(i).CP.equals("data type")) 
            {
                type = tokens.get(i).VP;
                i++;
                if (dec1() == true) 
                {
                    return true;
                }
            }
            else if (tokens.get(i).CP.equals("Identifier")) 
            {
                if (tokens.get(i).CP.equals("[")) 
                {
                i++;
                    if (tokens.get(i).CP.equals("]")) 
                    {
                        i++;
                        if (tokens.get(i).CP.equals("Identifier")) 
                        {
                            i++;
                            if (tokens.get(i).CP.equals("=")) 
                            {
                                i++;
                                if (tokens.get(i).CP.equals("new")) 
                                {
                                    if (arrayd() == true) 
                                    {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }return false;
    }
    boolean dec1()
    {
        if (tokens.get(i).CP.equals("Identifier") || tokens.get(i).CP.equals("["))
        {
            if (tokens.get(i).CP.equals("Identifier")) 
            {
                name = tokens.get(i).VP;
                i++;
                if (list() == true) 
                {
                    return true;
                }
            }
            else if (tokens.get(i).CP.equals("[")) 
            {
                i++;
                if (tokens.get(i).CP.equals("]")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("Identifier")) 
                    {
                        name = tokens.get(i).VP;
                        i++;
                        if (tokens.get(i).CP.equals("=")) 
                        {
                            i++;
                            if (tokens.get(i).CP.equals("new")) 
                            {
                                i++;
                                if (arrayd() == true) 
                                {
                                    if (tokens.get(i).CP.equals(";")) 
                                    {
                                        i++;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }return false;
    }
    boolean arrayd()
    {
        if (tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("data type")) 
        {
            if (tokens.get(i).CP.equals("Identifier")) 
            {
                i++;
                if (tokens.get(i).CP.equals("[")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("int constant")) 
                    {
                        i++;
                        if (tokens.get(i).CP.equals("]")) 
                        {
                            i++;
                            if (arrayd1() == true) 
                            {
                                return true;
                            }
                        }
                    }
                }
            }
            else if (tokens.get(i).CP.equals("data type")) 
            {
                
                if (!type.equals(tokens.get(i).VP)) 
                {
                    System.out.println("Mismatch Error");
                }
                i++;
                if (tokens.get(i).CP.equals("[")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("int constant")) 
                    {
                        i++;
                        if (tokens.get(i).CP.equals("]")) 
                        {
                            i++;
                            if (arrayd1() == true) 
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }return false;
    }
    boolean arrayd1()
    {
        if (tokens.get(i).CP.equals("[")) 
        {
            if (tokens.get(i).CP.equals("[")) 
            {
                i++;
                if (tokens.get(i).CP.equals("int constant")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("]")) 
                    {
                        i++;
                        if (arrayd1() == true) 
                        {
                            return true;
                        }
                    }
                }
            }else return true;
        }return true;
    }
    boolean OE()
    {
        if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("char constant")
            ||tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")
            ||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("(")) 
        {
            if (AE() == true) 
            {
                if (OE1() == true) 
                {
                    return true;
                }
            }
        }return false;
    }
    boolean OE1()
    {
        if (tokens.get(i).CP.equals("||")||tokens.get(i).CP.equals("else")||tokens.get(i).CP.equals("}")||tokens.get(i).CP.equals(";")) 
        {
            if (tokens.get(i).CP.equals("||")) 
            {
                i++;
                if (AE() == true) 
                {
                    if (OE1() == true) 
                    {
                     return true;    
                    }
                }
            }else return true;
        }return true;
    }
    boolean AE()
    {
        if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("char constant")
            ||tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")
            ||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("(")) 
        {
            if (RO() == true) 
            {
                if (AE1() == true) 
                {
                    return true;
                }
            }
        }return false;
    }
    boolean AE1()
    {
        if (tokens.get(i).CP.equals("&&")||tokens.get(i).CP.equals("else")||tokens.get(i).CP.equals("}")||tokens.get(i).CP.equals(";")) 
        {
            if (tokens.get(i).CP.equals("&&")) 
            {
                i++;
                if (RO() == true) 
                {
                    if (AE1() == true) 
                    {
                     return true;    
                    }
                }
            }else return true;
        }return true;
    }
    boolean RO()
    {
        if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("char constant")
            ||tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")
            ||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("(")) 
        {
            if (E() == true) 
            {
                if (RO1() == true) 
                {
                    return true;
                }
            }
        }return false;
    }
    boolean RO1()
    {
        if (tokens.get(i).CP.equals("relational operators")||tokens.get(i).CP.equals("else")||tokens.get(i).CP.equals("}")||tokens.get(i).CP.equals(";")) 
        {
            if (tokens.get(i).CP.equals("relational operators")) 
            {
                i++;
                if (E() == true) 
                {
                    if (RO1() == true) 
                    {
                     return true;    
                    }
                }
            }else return true;
        }return true;
    }
        boolean E()
        {
            if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("char constant")
                ||tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")
                ||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("(")) 
            {
                if (T() == true) 
                {
                    if (E1() == true) 
                    {
                        return true;
                    }
                }
            }return false;
        }
        boolean E1()
    {
        if (tokens.get(i).CP.equals("+-")||tokens.get(i).CP.equals("else")||tokens.get(i).CP.equals("}")||tokens.get(i).CP.equals(";")) 
        {
            if (tokens.get(i).CP.equals("+-")) 
            {
                i++;
                if (T() == true) 
                {
                    if (E1() == true) 
                    {
                     return true;    
                    }
                }
            }else return true;
        }return true;
    }
        boolean T()
        {
            if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("char constant")
                ||tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")
                ||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("(")) 
            {
                if (F() == true) 
                {
                    if (T1() == true) 
                    {
                        return true;
                    }
                }
            }return false;
        }
        boolean T1()
    {
        if (tokens.get(i).CP.equals("*/%")||tokens.get(i).CP.equals("else")||tokens.get(i).CP.equals("}")||tokens.get(i).CP.equals(";")) 
        {
            if (tokens.get(i).CP.equals("*/%")) 
            {
                i++;
                if (F() == true) 
                {
                    if (T1() == true) 
                    {
                     return true;    
                    }
                }
            }else return true;
        }return true;
    }
        boolean F()
        {
                if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("char constant")
                ||tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")
                ||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("(")) 
            {
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    return true;
                }
                else if(tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant"))
                {
                    i++;
                    return true;
                }
                else if(tokens.get(i).CP.equals("("))
                {
                    i++;
                    if (OE() == true) 
                    {
                        if (tokens.get(i).CP.equals(")")) 
                        {
                            i++;
                            return true;
                        }
                    }
                }
                else if (ref() == true) 
                {
                    return true;
                }
                else if (tokens.get(i).CP.equals("!")) 
                {
                    i++;
                    if (F() == true) 
                    {
                        return true;
                    }                    
                }
                else if (arrayc() == true) 
                {
                    return true;
                }
        }return false;
    }
        boolean arrayc()
        {
            if (tokens.get(i).CP.equals("Identifier")) 
            {
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("[")) 
                    {
                        i++;
                        if (c1() == true) 
                        {
                            if (tokens.get(i).CP.equals("]")) 
                            {
                                i++;
                                if (arrayc1() == true) 
                                {
                                return true;    
                                }
                            }    
                        }
                    }
                }
            }return false;
        }
        boolean arrayc1()
        {
            if (tokens.get(i).CP.equals("[")||tokens.get(i).CP.equals("*/%")) 
            {
                if (tokens.get(i).CP.equals("[")) 
                {
                    i++;
                    if (c1() == true) 
                    {
                        if (tokens.get(i).CP.equals("]")) 
                        {
                            i++;
                            if (arrayc1() == true) 
                            {
                                return true;
                            }
                        }
                    }
                }else return true;  
            }return false;
        }
        boolean c1()
        {
            if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("!")||tokens.get(i).CP.equals("char constant")
                ||tokens.get(i).CP.equals("bool constants")||tokens.get(i).CP.equals("int constant")||tokens.get(i).CP.equals("float constant")
                ||tokens.get(i).CP.equals("string constant")||tokens.get(i).CP.equals("(")) 
            {
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    return true;
                }
                else if (tokens.get(i).CP.equals("int constant")) 
                {
                    i++;
                    return true;
                }
                else if (OE() == true) 
                {
                    return true;
                }
            }return false;   
        }
        boolean args()
        {
            if (tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals(")")) 
            {
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    if (args1() == true) 
                    {
                        return true;
                    }
                }else return true;
            }return false;    
        }
        boolean args1()
        {
            if (tokens.get(i).CP.equals(",")||tokens.get(i).CP.equals(")")) 
            {
                if (tokens.get(i).CP.equals(",")) 
                {
                    i++;
                    if (tokens.get(i).CP.equals("Identifier")) 
                    {
                        i++;
                        if (args1() == true) 
                        {
                            return true;
                        }
                    }
                }else return true;
            }return false;
        }
        boolean IB()
        {
            if (tokens.get(i).VP.equals("public")) 
            {
                if (tokens.get(i).VP.equals("public")) 
                {
                    i++;
                    if (IB1() == true) 
                    {
                        return true;
                    }
                }
            }else if (tokens.get(i).VP.equals("}")) 
            {
                return true;
            }
            return false;
        }
        boolean IB1()
        {
            if (tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("data type")||tokens.get(i).CP.equals("abstract")
                ||tokens.get(i).CP.equals("static")||tokens.get(i).CP.equals("void")||tokens.get(i).CP.equals("final")) 
            {
                if (static1()== true) 
                {
                    if (final1() == true) 
                    {
                        if (declaration() == true) 
                        {
                            return true;
                        }
                    }
                }
                else if (abstract1() == true) 
                {
                    if (ret_type() == true) 
                    {
                        if (tokens.get(i).CP.equals("Identifier")) 
                        {
                            i++;
                            if (tokens.get(i).CP.equals("(")) 
                            {
                                i++;
                                if (para() == true) 
                                {
                                    if (tokens.get(i).CP.equals(")")) 
                                    {
                                        i++;
                                        if (tokens.get(i).CP.equals(";")) 
                                        {
                                            i++;
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }return false;
        }
        boolean abstract1()
        {
            if (tokens.get(i).CP.equals("abstract")||tokens.get(i).CP.equals("Identifier")
                ||tokens.get(i).CP.equals("data type")||tokens.get(i).CP.equals("void")) 
            {
                if (tokens.get(i).CP.equals("abstract")) 
                {
                    i++;
                    return true;
                }else return true;
            }return false;
        }
        boolean ret_type()
        {
            if (tokens.get(i).CP.equals("Identifier")||tokens.get(i).CP.equals("data type")
                ||tokens.get(i).CP.equals("void")) 
            {
                if (tokens.get(i).CP.equals("Identifier")) 
                {
                    i++;
                    return true;
                }
                else if (tokens.get(i).CP.equals("data type")) 
                {
                    i++;
                    return true;
                }
                else if (tokens.get(i).CP.equals("void"))
                {
                    i++;
                    return true;
                }
                else if (array() == true) 
                {
                    return true;
                }
            }return false;
        }
        boolean ts()
        {
            if (tokens.get(i).CP.equals("refer")||tokens.get(i).CP.equals("Identifer")) 
            {
                if (tokens.get(i).CP.equals("refer")) 
                {
                    i++;
                    return true;
                }
            }return true;
        }
}
interface A{
abstract int num();
}
