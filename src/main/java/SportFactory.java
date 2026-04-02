public class SportFactory {
    public static Sport createSport(String sportType){
        if(sportType.equalsIgnoreCase("football")){
            return new Football();
        }
        throw new IllegalArgumentException("Unknown sport");
    }
}
