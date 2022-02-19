package compiler.construction;

import java.util.ArrayList;
import java.util.Stack;

public class SemanticAnalyzer {
    SemanticAnalyzer(){};
    ArrayList<MainTable> maintable = new ArrayList<MainTable>();
    ArrayList<FunctionTable> functable = new ArrayList<FunctionTable>();
    int scopenum = 0;
    Stack currentscope = new Stack();
    public static void main(String[] args) {
       
    }
boolean insertMT(String name, String type, String CM, ArrayList parent)
{
    MainTable m = new MainTable(name, type, CM, parent);
    if (maintable.contains(m) == true) 
    {
        return false;
    }
    else
    {
        maintable.add(m);
        return true;
    }
}
MainTable lookupMT(String name)
{
    for (int i = 0; i < maintable.size(); i++) 
    {
        if (maintable.get(i).name.equals(name)) 
        {
            return maintable.get(i);
        }
    }
    return null;
}
boolean insertCT(String name, String type, String AM, boolean Fin, String TM, String ClassName)
{
    ClassTable c = new ClassTable(name, type, AM, Fin, TM);
    for (int i = 0; i < maintable.size(); i++) 
    {
        MainTable m = maintable.get(i);
        if (m.name.equals(ClassName)) 
        {
            m.ct.add(c);
            return true;
        }
    }return false;
}
ClassTable LookupCTa(String Name, String ClassName)
{
    for (int i = 0; i < maintable.size(); i++) 
    {
        MainTable m = maintable.get(i);
        if (m.name.equals(ClassName)) 
        {
            for (int j = 0; j < m.ct.size(); j++) 
            {
                ClassTable c = m.ct.get(j);
                if (c.name.equals(Name)) 
                {
                    return c;
                }
            }
        }
    }
    return null;
}
ClassTable LookupCTf(String Name, String PL, String ClassName)
{
    for (int i = 0; i < maintable.size(); i++) 
    {
        MainTable m = maintable.get(i);
        if (m.name.equals(ClassName)) 
        {
            for (int j = 0; j < m.ct.size(); j++) 
            {
                ClassTable c = m.ct.get(j);
                if (c.name.equals(Name)) 
                {
                    if (c.type.equals(PL)) 
                    {
                        return c;
                    }
                }
            }
        }
    }
    return null;
}
boolean insertFT(String name, String type, int scope)
{

        for (FunctionTable item : functable) {
            if (item.name.equals(name) && item.scope == scope) {
                return false;
            }
        }

        functable.add(new FunctionTable(name, type, scope));
        System.out.println(name + " " + type + " " + scope);
        return true;
}
FunctionTable lookupFT(String name,int scope)
{
    for (int i = 0; i < functable.size(); i++) 
    {
        if (functable.get(i).name.equals(name) && functable.get(i).scope == scope) 
        {
            return functable.get(i);
        }
    }return null;
}

public void createScope()
{
    scopenum++;
    currentscope.push(scopenum);
}
public void destroyScope()
{
    currentscope.pop();
}
public StringBuilder compatibilityCheck(StringBuilder leftType, StringBuilder rightType, String operator) {

        if (operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/") || operator.equals("%")) {
            if (leftType.equals("SignedInteger") && rightType.equals("SignedInteger")) {
                StringBuilder type = new StringBuilder("SignedInteger");
                return type;
            }
        }
        return null;
    }
}

class MainTable
{
    String name = null;
    String type = null;
    String CM = null;
    ArrayList parent = new ArrayList();
    ArrayList<ClassTable> ct = new ArrayList<ClassTable>();
    int link = ct.hashCode();

    MainTable(String name, String type, String CM, ArrayList parent)
    {
        this.name = name;
        this.type = type;
        this.CM = CM;
        this.parent = parent;
    }
}
class ClassTable
{
    String name = null;
    String type = null;
    String AM = null;
    boolean Final = false;
    String TM = null;

    ClassTable(String name, String type, String AM, boolean Final, String TM)
    {
        this.name = name;
        this.type = type;
        this.AM = AM;
        this.TM = TM;
        this.Final = Final;
    }
}
class FunctionTable
{
    String name = null;
    String type = null;
    int scope = 0;

    FunctionTable(String name, String type, int scope)
    {
        this.name = name;
        this.type = type;
        this.scope = scope;
    }
}
