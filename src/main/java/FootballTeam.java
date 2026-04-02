public class FootballTeam extends Team{
    private static final int MAX_PLAYERS=11;
    private static final  int MAX_SUBSTITUTES=12;
    private static final int MAX_SUBSTITUTIONS = 5;
    private static final int MAX_TEAM_PLAYERS=23;




    public FootballTeam(String teamName, String coachName) {
        super(teamName, coachName);
    }



    public void addPlayerToTeam(Player player){
        if(this.getTeamPlayerCount()<MAX_TEAM_PLAYERS){
           this.addPlayer(player);
        }
    }


    public void addPlayerToSubstitute(Player player){
        if(this.getTeamSubstituteCount()<MAX_SUBSTITUTES){
            this.addSubstitute(player);
        }
    }




    @Override
    public int getPlayersOnField(){
        return this.MAX_PLAYERS;
    }











}
