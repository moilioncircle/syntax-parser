#

import java.util.List;

import com.leon.generator.BaseCodeGenerator;
import com.leon.simple.json.ValueType;
import com.leon.simple.json.Value;
import com.leon.simple.json.Pair;
import com.leon.simple.json.Member;
import com.leon.simple.json.JsonObject;
import com.leon.simple.json.JsonArray;
import com.leon.simple.json.Json;
import com.leon.simple.json.Element;
import com.leon.simple.json.Root;
/**
 * @author : Leon
 * @since : 2013-9-12
 * @see :
 */

public class JsonGenerator implements BaseCodeGenerator{
#
%name : EOF 20 20;
%name : OBJECTBEGIN 10 10;
%name : OBJECTEND 3 3;
%name : COMMA 1 1;
%name : COLON 7 7;
%name : ARRAYBEGIN 9 9;
%name : ARRAYEND 2 2;
%name : TRUE 4 4;
%name : FALSE 5 5;
%name : NULL 6 6;
%name : NUMBER 7 7;
%name : STRING 8 8;

%start : json;
%class JsonGenerator;
%%
json            : root EOF #new Json($0)#
                ;
root            : object #new Root($0)#
                | array #new Root($0)#
                ;
object          : OBJECTBEGIN OBJECTEND #new JsonObject()#
                | OBJECTBEGIN members OBJECTEND #new JsonObject($1)#
                ;
members         : pair #new Member($0)#
                | pair COMMA members #new Member($0,$2)#
                ;
pair            : STRING COLON value #new Pair($0,$2)#
                ;
array           : ARRAYBEGIN ARRAYEND #new JsonArray()#
                | ARRAYBEGIN elements ARRAYEND #new JsonArray($1)#
                ;
elements        : value #new Element($0)#
                | value COMMA elements #new Element($0,$2)#
                ;
value           : STRING #new Value($0,ValueType.STRING)#
                | NUMBER #new Value($0,ValueType.NUMBER)#
                | object #new Value($0,ValueType.OBJECT)#
                | array #new Value($0,ValueType.ARRAY)#
                | TRUE #new Value($0,ValueType.TRUE)#
                | FALSE #new Value($0,ValueType.FALSE)#
                | NULL #new Value($0,ValueType.NULL)#
                ;
%%
#
}
#