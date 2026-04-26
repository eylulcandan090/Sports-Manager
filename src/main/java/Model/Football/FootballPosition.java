package Model.Football;

public enum FootballPosition {
    GK(FootballPositionGroup.GOALKEEPER),

    CB(FootballPositionGroup.DEFENDER),
    RB(FootballPositionGroup.DEFENDER),
    LB(FootballPositionGroup.DEFENDER),

    CDM(FootballPositionGroup.MIDFIELDER),
    CM(FootballPositionGroup.MIDFIELDER),
    CAM(FootballPositionGroup.MIDFIELDER),

    RW(FootballPositionGroup.ATTACKER),
    LW(FootballPositionGroup.ATTACKER),
    ST(FootballPositionGroup.ATTACKER);

    private final FootballPositionGroup group;

    FootballPosition(FootballPositionGroup group) {
        this.group = group;
    }

    public FootballPositionGroup getGroup() {
        return group;
    }
}
