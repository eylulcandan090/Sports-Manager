package Model;

import Model.Basketball.Basketball;
import Model.Football.Football;

public class SportFactory {
   public Sport createSport(String type){
       switch (type.toLowerCase()){
           case "football":
               return new Football();
           case "basketball":
               return new Basketball();
       }
       throw new IllegalArgumentException();
   }
}
