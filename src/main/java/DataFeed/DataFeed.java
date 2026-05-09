package DataFeed;

import Database.Database;
import Repository.*;
import Repository.FootballPlayerRepo;
import Service.*;

import java.sql.Connection;

public class DataFeed {

    public static void feed() {
        Database database = Database.getInstance();
        Connection connection = database.getConnection();
        CoachRepo coachRepo = new CoachRepo(connection);

        FootballPlayerRepo footballPlayerRepo = new FootballPlayerRepo(connection);
        FootballPlayerService footballPlayerService = new FootballPlayerService(footballPlayerRepo, coachRepo);

        SportRepo sportRepo = new SportRepo(connection);
        SportService sportService = new SportService(sportRepo);

        TeamRepo teamRepo = new TeamRepo(connection);
        TeamService teamService = new TeamService(teamRepo);


        LeagueRepo leagueRepo = new LeagueRepo(connection);
        LeagueService leagueService = new LeagueService(leagueRepo);


        int footballId = sportService.getOrSave("Football");
        int basketballId = sportService.getOrSave("Basketball");

        int premierLeague = leagueService.getOrSave("Premier League", footballId);
        int nbaLeague = leagueService.getOrSave("NBA", basketballId);


        int arsenalId = teamService.getOrSave("Arsenal", premierLeague, footballId);

        if (!footballPlayerRepo.hasPlayers(arsenalId)) {

            // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
            footballPlayerService.save("David Raya", 28, 0, arsenalId, 20, 75, 88, "GK", 15);
            footballPlayerService.save("William Saliba", 23, 0, arsenalId, 35, 82, 10, "CB", 85);
            footballPlayerService.save("Gabriel Magalhães", 26, 0, arsenalId, 40, 78, 10, "CB", 84);
            footballPlayerService.save("Ben White", 26, 0, arsenalId, 45, 81, 10, "RB", 82);
            footballPlayerService.save("Riccardo Calafiori", 22, 0, arsenalId, 50, 80, 10, "LB", 81);
            footballPlayerService.save("Declan Rice", 25, 0, arsenalId, 68, 86, 10, "CDM", 83);
            footballPlayerService.save("Martin Ødegaard", 25, 0, arsenalId, 82, 92, 10, "CAM", 58);
            footballPlayerService.save("Mikel Merino", 28, 0, arsenalId, 70, 84, 10, "CM", 75);
            footballPlayerService.save("Bukayo Saka", 22, 0, arsenalId, 85, 85, 10, "RW", 55);
            footballPlayerService.save("Gabriel Martinelli", 23, 0, arsenalId, 80, 78, 10, "LW", 45);
            footballPlayerService.save("Kai Havertz", 25, 0, arsenalId, 82, 80, 10, "ST", 48);
            footballPlayerService.save("Leandro Trossard", 29, 0, arsenalId, 84, 82, 10, "LW", 35);
            footballPlayerService.save("Jurriën Timber", 23, 0, arsenalId, 40, 83, 10, "RB", 82);
            footballPlayerService.save("Oleksandr Zinchenko", 27, 0, arsenalId, 65, 88, 10, "LB", 72);
            footballPlayerService.save("Thomas Partey", 31, 0, arsenalId, 72, 84, 10, "CDM", 80);
            footballPlayerService.save("Gabriel Jesus", 27, 0, arsenalId, 80, 79, 10, "ST", 42);
            footballPlayerService.save("Jorginho", 32, 0, arsenalId, 65, 90, 10, "CM", 73);
            footballPlayerService.save("Neto", 35, 0, arsenalId, 15, 65, 80, "GK", 12);


            int manCityId = teamService.getOrSave("Manchester City", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(manCityId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Ederson", 30, 0, manCityId, 20, 93, 89, "GK", 12);
                footballPlayerService.save("Ruben Dias", 27, 0, manCityId, 38, 80, 10, "CB", 89);
                footballPlayerService.save("John Stones", 30, 0, manCityId, 50, 88, 10, "CB", 85);
                footballPlayerService.save("Kyle Walker", 34, 0, manCityId, 55, 78, 10, "RB", 81);
                footballPlayerService.save("Josko Gvardiol", 22, 0, manCityId, 65, 84, 10, "LB", 83);
                footballPlayerService.save("Rodri", 28, 0, manCityId, 80, 91, 10, "CDM", 87);
                footballPlayerService.save("Kevin De Bruyne", 33, 0, manCityId, 88, 94, 10, "CAM", 50);
                footballPlayerService.save("Bernardo Silva", 29, 0, manCityId, 78, 90, 10, "CM", 70);
                footballPlayerService.save("Phil Foden", 24, 0, manCityId, 86, 88, 10, "RW", 56);
                footballPlayerService.save("Jack Grealish", 28, 0, manCityId, 76, 86, 10, "LW", 45);
                footballPlayerService.save("Erling Haaland", 23, 0, manCityId, 95, 65, 10, "ST", 40);
                footballPlayerService.save("Mateo Kovacic", 30, 0, manCityId, 68, 88, 10, "CM", 72);
                footballPlayerService.save("Jeremy Doku", 22, 0, manCityId, 78, 75, 10, "LW", 30);
                footballPlayerService.save("Manuel Akanji", 28, 0, manCityId, 45, 83, 10, "CB", 84);
                footballPlayerService.save("Nathan Ake", 29, 0, manCityId, 55, 81, 10, "CB", 85);
                footballPlayerService.save("Ilkay Gündogan", 33, 0, manCityId, 82, 89, 10, "CM", 72);
                footballPlayerService.save("Savinho", 20, 0, manCityId, 77, 80, 10, "RW", 42);
                footballPlayerService.save("Stefan Ortega", 31, 0, manCityId, 15, 82, 82, "GK", 10);

            }

            int liverpoolId = teamService.getOrSave("Liverpool", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(liverpoolId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Alisson Becker", 31, 0, liverpoolId, 15, 78, 90, "GK", 14);
                footballPlayerService.save("Virgil van Dijk", 32, 0, liverpoolId, 60, 81, 10, "CB", 90);
                footballPlayerService.save("Ibrahima Konate", 25, 0, liverpoolId, 30, 70, 10, "CB", 84);
                footballPlayerService.save("Trent Alexander-Arnold", 25, 0, liverpoolId, 75, 92, 10, "RB", 75);
                footballPlayerService.save("Andrew Robertson", 30, 0, liverpoolId, 62, 84, 10, "LB", 80);
                footballPlayerService.save("Alexis Mac Allister", 25, 0, liverpoolId, 80, 88, 10, "CM", 76);
                footballPlayerService.save("Dominik Szoboszlai", 23, 0, liverpoolId, 84, 87, 10, "CM", 68);
                footballPlayerService.save("Ryan Gravenberch", 22, 0, liverpoolId, 74, 83, 10, "CDM", 79);
                footballPlayerService.save("Mohamed Salah", 32, 0, liverpoolId, 91, 84, 10, "RW", 45);
                footballPlayerService.save("Luis Diaz", 27, 0, liverpoolId, 82, 78, 10, "LW", 42);
                footballPlayerService.save("Diogo Jota", 27, 0, liverpoolId, 85, 77, 10, "ST", 52);
                footballPlayerService.save("Darwin Nuñez", 25, 0, liverpoolId, 81, 72, 10, "ST", 40);
                footballPlayerService.save("Cody Gakpo", 25, 0, liverpoolId, 83, 81, 10, "LW", 48);
                footballPlayerService.save("Federico Chiesa", 26, 0, liverpoolId, 82, 76, 10, "RW", 38);
                footballPlayerService.save("Harvey Elliott", 21, 0, liverpoolId, 76, 83, 10, "CAM", 50);
                footballPlayerService.save("Curtis Jones", 23, 0, liverpoolId, 74, 82, 10, "CM", 72);
                footballPlayerService.save("Kostas Tsimikas", 28, 0, liverpoolId, 58, 80, 10, "LB", 77);
                footballPlayerService.save("Caoimhin Kelleher", 25, 0, liverpoolId, 15, 72, 81, "GK", 12);

            }

            int chelseaId = teamService.getOrSave("Chelsea", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(chelseaId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Robert Sanchez", 26, 0, chelseaId, 15, 70, 81, "GK", 14);
                footballPlayerService.save("Levi Colwill", 21, 0, chelseaId, 35, 78, 10, "CB", 82);
                footballPlayerService.save("Wesley Fofana", 23, 0, chelseaId, 30, 68, 10, "CB", 81);
                footballPlayerService.save("Reece James", 24, 0, chelseaId, 75, 84, 10, "RB", 83);
                footballPlayerService.save("Marc Cucurella", 25, 0, chelseaId, 55, 79, 10, "LB", 78);
                footballPlayerService.save("Moises Caicedo", 22, 0, chelseaId, 65, 85, 10, "CDM", 84);
                footballPlayerService.save("Enzo Fernandez", 23, 0, chelseaId, 78, 89, 10, "CM", 74);
                footballPlayerService.save("Cole Palmer", 22, 0, chelseaId, 86, 88, 10, "CAM", 48);
                footballPlayerService.save("Noni Madueke", 22, 0, chelseaId, 80, 76, 10, "RW", 40);
                footballPlayerService.save("Jadon Sancho", 24, 0, chelseaId, 77, 83, 10, "LW", 35);
                footballPlayerService.save("Nicolas Jackson", 23, 0, chelseaId, 81, 72, 10, "ST", 44);
                footballPlayerService.save("Christopher Nkunku", 26, 0, chelseaId, 85, 82, 10, "ST", 38);
                footballPlayerService.save("Pedro Neto", 24, 0, chelseaId, 79, 81, 10, "RW", 42);
                footballPlayerService.save("Joao Felix", 24, 0, chelseaId, 82, 81, 10, "CAM", 36);
                footballPlayerService.save("Romeo Lavia", 20, 0, chelseaId, 60, 82, 10, "CDM", 79);
                footballPlayerService.save("Malo Gusto", 21, 0, chelseaId, 50, 81, 10, "RB", 78);
                footballPlayerService.save("Axel Disasi", 26, 0, chelseaId, 45, 70, 10, "CB", 79);
                footballPlayerService.save("Filip Jorgensen", 22, 0, chelseaId, 15, 68, 79, "GK", 12);

            }

            int manUtdId = teamService.getOrSave("Manchester United", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(manUtdId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Andre Onana", 28, 0, manUtdId, 20, 85, 83, "GK", 15);
                footballPlayerService.save("Matthijs de Ligt", 24, 0, manUtdId, 45, 76, 10, "CB", 84);
                footballPlayerService.save("Lisandro Martinez", 26, 0, manUtdId, 40, 83, 10, "CB", 85);
                footballPlayerService.save("Diogo Dalot", 25, 0, manUtdId, 68, 81, 10, "RB", 79);
                footballPlayerService.save("Noussair Mazraoui", 26, 0, manUtdId, 60, 82, 10, "LB", 78);
                footballPlayerService.save("Manuel Ugarte", 23, 0, manUtdId, 55, 80, 10, "CDM", 83);
                footballPlayerService.save("Kobbie Mainoo", 19, 0, manUtdId, 72, 84, 10, "CM", 70);
                footballPlayerService.save("Bruno Fernandes", 29, 0, manUtdId, 85, 90, 10, "CAM", 55);
                footballPlayerService.save("Marcus Rashford", 26, 0, manUtdId, 83, 78, 10, "LW", 38);
                footballPlayerService.save("Alejandro Garnacho", 19, 0, manUtdId, 80, 76, 10, "RW", 40);
                footballPlayerService.save("Rasmus Højlund", 21, 0, manUtdId, 82, 68, 10, "ST", 42);
                footballPlayerService.save("Joshua Zirkzee", 23, 0, manUtdId, 79, 80, 10, "ST", 40);
                footballPlayerService.save("Amad Diallo", 21, 0, manUtdId, 76, 78, 10, "RW", 45);
                footballPlayerService.save("Mason Mount", 25, 0, manUtdId, 78, 83, 10, "CAM", 62);
                footballPlayerService.save("Casemiro", 32, 0, manUtdId, 73, 81, 10, "CDM", 81);
                footballPlayerService.save("Harry Maguire", 31, 0, manUtdId, 50, 75, 10, "CB", 80);
                footballPlayerService.save("Leny Yoro", 18, 0, manUtdId, 30, 75, 10, "CB", 78);
                footballPlayerService.save("Altay Bayindir", 26, 0, manUtdId, 10, 65, 78, "GK", 12);
            }

            int tottenhamId = teamService.getOrSave("Tottenham Hotspur", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(tottenhamId)) {
                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Guglielmo Vicario", 27, 0, tottenhamId, 15, 70, 85, "GK", 13);
                footballPlayerService.save("Cristian Romero", 26, 0, tottenhamId, 45, 76, 10, "CB", 86);
                footballPlayerService.save("Micky van de Ven", 23, 0, tottenhamId, 40, 78, 10, "CB", 84);
                footballPlayerService.save("Pedro Porro", 24, 0, tottenhamId, 78, 84, 10, "RB", 74);
                footballPlayerService.save("Destiny Udogie", 21, 0, tottenhamId, 62, 80, 10, "LB", 76);
                footballPlayerService.save("Yves Bissouma", 27, 0, tottenhamId, 65, 83, 10, "CDM", 82);
                footballPlayerService.save("James Maddison", 27, 0, tottenhamId, 84, 89, 10, "CAM", 45);
                footballPlayerService.save("Pape Matar Sarr", 21, 0, tottenhamId, 72, 81, 10, "CM", 72);
                footballPlayerService.save("Son Heung-min", 31, 0, tottenhamId, 90, 83, 10, "LW", 42);
                footballPlayerService.save("Dejan Kulusevski", 24, 0, tottenhamId, 80, 85, 10, "RW", 58);
                footballPlayerService.save("Dominic Solanke", 26, 0, tottenhamId, 83, 72, 10, "ST", 48);
                footballPlayerService.save("Brennan Johnson", 23, 0, tottenhamId, 79, 75, 10, "RW", 38);
                footballPlayerService.save("Richarlison", 27, 0, tottenhamId, 81, 70, 10, "ST", 52);
                footballPlayerService.save("Rodrigo Bentancur", 27, 0, tottenhamId, 70, 85, 10, "CM", 78);
                footballPlayerService.save("Lucas Bergvall", 18, 0, tottenhamId, 68, 80, 10, "CM", 60);
                footballPlayerService.save("Radu Dragusin", 22, 0, tottenhamId, 35, 70, 10, "CB", 80);
                footballPlayerService.save("Timo Werner", 28, 0, tottenhamId, 77, 72, 10, "LW", 35);
                footballPlayerService.save("Fraser Forster", 36, 0, tottenhamId, 10, 60, 77, "GK", 10);
            }


            int astonVillaId = teamService.getOrSave("Aston Villa", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(astonVillaId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Emiliano Martinez", 31, 0, astonVillaId, 20, 75, 89, "GK", 14);
                footballPlayerService.save("Ezri Konsa", 26, 0, astonVillaId, 35, 76, 10, "CB", 85);
                footballPlayerService.save("Pau Torres", 27, 0, astonVillaId, 45, 85, 10, "CB", 83);
                footballPlayerService.save("Matty Cash", 26, 0, astonVillaId, 68, 78, 10, "RB", 77);
                footballPlayerService.save("Ian Maatsen", 22, 0, astonVillaId, 65, 80, 10, "LB", 74);
                footballPlayerService.save("Amadou Onana", 22, 0, astonVillaId, 68, 81, 10, "CDM", 82);
                footballPlayerService.save("Youri Tielemans", 27, 0, astonVillaId, 82, 88, 10, "CM", 70);
                footballPlayerService.save("John McGinn", 29, 0, astonVillaId, 78, 84, 10, "CM", 76);
                footballPlayerService.save("Leon Bailey", 26, 0, astonVillaId, 83, 80, 10, "RW", 38);
                footballPlayerService.save("Morgan Rogers", 21, 0, astonVillaId, 79, 78, 10, "CAM", 46);
                footballPlayerService.save("Ollie Watkins", 28, 0, astonVillaId, 88, 76, 10, "ST", 44);
                footballPlayerService.save("Jhon Duran", 20, 0, astonVillaId, 84, 65, 10, "ST", 35);
                footballPlayerService.save("Jacob Ramsey", 23, 0, astonVillaId, 77, 81, 10, "CM", 65);
                footballPlayerService.save("Lucas Digne", 30, 0, astonVillaId, 65, 83, 10, "LB", 78);
                footballPlayerService.save("Diego Carlos", 31, 0, astonVillaId, 40, 72, 10, "CB", 81);
                footballPlayerService.save("Ross Barkley", 30, 0, astonVillaId, 78, 84, 10, "CM", 62);
                footballPlayerService.save("Emiliano Buendia", 27, 0, astonVillaId, 79, 84, 10, "CAM", 50);
                footballPlayerService.save("Robin Olsen", 34, 0, astonVillaId, 10, 62, 76, "GK", 12);

            }


            int newcastleId = teamService.getOrSave("Newcastle United", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(newcastleId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Nick Pope", 32, 0, newcastleId, 15, 60, 85, "GK", 12);
                footballPlayerService.save("Sven Botman", 24, 0, newcastleId, 35, 78, 10, "CB", 85);
                footballPlayerService.save("Fabian Schär", 32, 0, newcastleId, 70, 81, 10, "CB", 82);
                footballPlayerService.save("Kieran Trippier", 33, 0, newcastleId, 72, 89, 10, "RB", 78);
                footballPlayerService.save("Lewis Hall", 19, 0, newcastleId, 65, 80, 10, "LB", 72);
                footballPlayerService.save("Bruno Guimarães", 26, 0, newcastleId, 78, 88, 10, "CM", 76);
                footballPlayerService.save("Joelinton", 27, 0, newcastleId, 75, 80, 10, "CM", 80);
                footballPlayerService.save("Sandro Tonali", 24, 0, newcastleId, 74, 85, 10, "CDM", 79);
                footballPlayerService.save("Anthony Gordon", 23, 0, newcastleId, 82, 81, 10, "LW", 55);
                footballPlayerService.save("Harvey Barnes", 26, 0, newcastleId, 84, 78, 10, "LW", 35);
                footballPlayerService.save("Alexander Isak", 24, 0, newcastleId, 90, 78, 10, "ST", 40);
                footballPlayerService.save("Callum Wilson", 32, 0, newcastleId, 85, 68, 10, "ST", 32);
                footballPlayerService.save("Jacob Murphy", 29, 0, newcastleId, 77, 79, 10, "RW", 48);
                footballPlayerService.save("Joe Willock", 24, 0, newcastleId, 76, 79, 10, "CM", 68);
                footballPlayerService.save("Dan Burn", 32, 0, newcastleId, 40, 72, 10, "CB", 81);
                footballPlayerService.save("Tino Livramento", 21, 0, newcastleId, 55, 78, 10, "RB", 77);
                footballPlayerService.save("Lloyd Kelly", 25, 0, newcastleId, 30, 74, 10, "CB", 79);
                footballPlayerService.save("Martin Dubravka", 35, 0, newcastleId, 10, 68, 79, "GK", 10);

            }


            int brightonId = teamService.getOrSave("Brighton", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(brightonId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Bart Verbruggen", 21, 0, brightonId, 15, 80, 81, "GK", 14);
                footballPlayerService.save("Lewis Dunk", 32, 0, brightonId, 50, 80, 10, "CB", 84);
                footballPlayerService.save("Jan Paul van Hecke", 24, 0, brightonId, 30, 79, 10, "CB", 82);
                footballPlayerService.save("Joel Veltman", 32, 0, brightonId, 45, 75, 10, "RB", 81);
                footballPlayerService.save("Pervis Estupiñán", 26, 0, brightonId, 65, 81, 10, "LB", 76);
                footballPlayerService.save("Carlos Baleba", 20, 0, brightonId, 60, 82, 10, "CDM", 79);
                footballPlayerService.save("Mats Wieffer", 24, 0, brightonId, 68, 83, 10, "CDM", 82);
                footballPlayerService.save("Joao Pedro", 22, 0, brightonId, 84, 80, 10, "CAM", 45);
                footballPlayerService.save("Kaoru Mitoma", 27, 0, brightonId, 80, 82, 10, "LW", 38);
                footballPlayerService.save("Yankuba Minteh", 19, 0, brightonId, 78, 74, 10, "RW", 35);
                footballPlayerService.save("Danny Welbeck", 33, 0, brightonId, 81, 75, 10, "ST", 48);
                footballPlayerService.save("Evan Ferguson", 19, 0, brightonId, 83, 68, 10, "ST", 32);
                footballPlayerService.save("Simon Adingra", 22, 0, brightonId, 79, 76, 10, "RW", 40);
                footballPlayerService.save("Georginio Rutter", 22, 0, brightonId, 77, 81, 10, "CAM", 42);
                footballPlayerService.save("Matt O'Riley", 23, 0, brightonId, 80, 85, 10, "CM", 68);
                footballPlayerService.save("Ferdi Kadıoğlu", 24, 0, brightonId, 72, 84, 10, "LB", 75);
                footballPlayerService.save("Igor Julio", 26, 0, brightonId, 35, 77, 10, "CB", 78);
                footballPlayerService.save("Jason Steele", 33, 0, brightonId, 15, 84, 77, "GK", 12);

            }

            int westHamId = teamService.getOrSave("West Ham United", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(westHamId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Alphonse Areola", 31, 0, westHamId, 15, 68, 84, "GK", 13);
                footballPlayerService.save("Max Kilman", 27, 0, westHamId, 35, 79, 10, "CB", 83);
                footballPlayerService.save("Jean-Clair Todibo", 24, 0, westHamId, 30, 77, 10, "CB", 81);
                footballPlayerService.save("Aaron Wan-Bissaka", 26, 0, westHamId, 45, 74, 10, "RB", 88);
                footballPlayerService.save("Emerson Palmieri", 29, 0, westHamId, 64, 80, 10, "LB", 75);
                footballPlayerService.save("Guido Rodriguez", 30, 0, westHamId, 60, 83, 10, "CDM", 82);
                footballPlayerService.save("Edson Alvarez", 26, 0, westHamId, 65, 81, 10, "CDM", 84);
                footballPlayerService.save("Lucas Paqueta", 26, 0, westHamId, 82, 89, 10, "CAM", 65);
                footballPlayerService.save("Mohammed Kudus", 23, 0, westHamId, 84, 80, 10, "RW", 45);
                footballPlayerService.save("Jarrod Bowen", 27, 0, westHamId, 86, 82, 10, "RW", 48);
                footballPlayerService.save("Niclas Füllkrug", 31, 0, westHamId, 85, 72, 10, "ST", 38);
                footballPlayerService.save("Crysencio Summerville", 22, 0, westHamId, 80, 78, 10, "LW", 32);
                footballPlayerService.save("Tomas Soucek", 29, 0, westHamId, 78, 75, 10, "CM", 78);
                footballPlayerService.save("Carlos Soler", 27, 0, westHamId, 77, 85, 10, "CM", 60);
                footballPlayerService.save("Konstantinos Mavropanos", 26, 0, westHamId, 40, 70, 10, "CB", 79);
                footballPlayerService.save("Vladimir Coufal", 31, 0, westHamId, 58, 77, 10, "RB", 78);
                footballPlayerService.save("Michail Antonio", 34, 0, westHamId, 80, 68, 10, "ST", 42);
                footballPlayerService.save("Lukasz Fabianski", 39, 0, westHamId, 10, 65, 78, "GK", 11);

            }

            int fulhamId = teamService.getOrSave("Fulham", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(fulhamId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Bernd Leno", 32, 0, fulhamId, 15, 72, 84, "GK", 14);
                footballPlayerService.save("Joachim Andersen", 28, 0, fulhamId, 55, 84, 10, "CB", 85);
                footballPlayerService.save("Calvin Bassey", 24, 0, fulhamId, 35, 75, 10, "CB", 80);
                footballPlayerService.save("Kenny Tete", 28, 0, fulhamId, 58, 77, 10, "RB", 82);
                footballPlayerService.save("Antonee Robinson", 26, 0, fulhamId, 62, 80, 10, "LB", 78);
                footballPlayerService.save("Sander Berge", 26, 0, fulhamId, 68, 83, 10, "CDM", 81);
                footballPlayerService.save("Andreas Pereira", 28, 0, fulhamId, 80, 86, 10, "CAM", 48);
                footballPlayerService.save("Emile Smith Rowe", 24, 0, fulhamId, 79, 84, 10, "CAM", 40);
                footballPlayerService.save("Alex Iwobi", 28, 0, fulhamId, 76, 82, 10, "RW", 52);
                footballPlayerService.save("Adama Traore", 28, 0, fulhamId, 72, 74, 10, "RW", 30);
                footballPlayerService.save("Raul Jimenez", 33, 0, fulhamId, 82, 75, 10, "ST", 45);
                footballPlayerService.save("Rodrigo Muniz", 23, 0, fulhamId, 81, 65, 10, "ST", 38);
                footballPlayerService.save("Harry Wilson", 27, 0, fulhamId, 82, 81, 10, "RW", 42);
                footballPlayerService.save("Harrison Reed", 29, 0, fulhamId, 65, 80, 10, "CM", 76);
                footballPlayerService.save("Timothy Castagne", 28, 0, fulhamId, 60, 78, 10, "RB", 77);
                footballPlayerService.save("Sasa Lukic", 27, 0, fulhamId, 70, 82, 10, "CM", 74);
                footballPlayerService.save("Issa Diop", 27, 0, fulhamId, 30, 70, 10, "CB", 79);
                footballPlayerService.save("Steven Benda", 25, 0, fulhamId, 10, 60, 74, "GK", 12);
            }

            int brentfordId = teamService.getOrSave("Brentford", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(brentfordId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Mark Flekken", 31, 0, brentfordId, 15, 75, 82, "GK", 14);
                footballPlayerService.save("Ethan Pinnock", 31, 0, brentfordId, 40, 72, 10, "CB", 84);
                footballPlayerService.save("Nathan Collins", 23, 0, brentfordId, 35, 74, 10, "CB", 81);
                footballPlayerService.save("Kristoffer Ajer", 26, 0, brentfordId, 55, 78, 10, "RB", 79);
                footballPlayerService.save("Rico Henry", 27, 0, brentfordId, 60, 77, 10, "LB", 76);
                footballPlayerService.save("Christian Nørgaard", 30, 0, brentfordId, 65, 82, 10, "CDM", 83);
                footballPlayerService.save("Vitaly Janelt", 26, 0, brentfordId, 70, 80, 10, "CM", 77);
                footballPlayerService.save("Mathias Jensen", 28, 0, brentfordId, 74, 85, 10, "CM", 68);
                footballPlayerService.save("Bryan Mbeumo", 24, 0, brentfordId, 85, 81, 10, "RW", 45);
                footballPlayerService.save("Yoane Wissa", 27, 0, brentfordId, 83, 76, 10, "LW", 42);
                footballPlayerService.save("Kevin Schade", 22, 0, brentfordId, 78, 72, 10, "ST", 35);
                footballPlayerService.save("Mikkel Damsgaard", 24, 0, brentfordId, 72, 83, 10, "CAM", 48);
                footballPlayerService.save("Fabio Carvalho", 21, 0, brentfordId, 77, 81, 10, "CAM", 40);
                footballPlayerService.save("Sepp van den Berg", 22, 0, brentfordId, 30, 75, 10, "CB", 79);
                footballPlayerService.save("Gustavo Nunes", 18, 0, brentfordId, 75, 74, 10, "LW", 32);
                footballPlayerService.save("Keane Lewis-Potter", 23, 0, brentfordId, 76, 75, 10, "LW", 44);
                footballPlayerService.save("Kristoffer Ajer", 26, 0, brentfordId, 55, 78, 10, "CB", 82);
                footballPlayerService.save("Hakon Valdimarsson", 22, 0, brentfordId, 10, 60, 75, "GK", 11);

            }

            int crystalPalaceId = teamService.getOrSave("Crystal Palace", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(crystalPalaceId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Dean Henderson", 27, 0, crystalPalaceId, 15, 70, 82, "GK", 14);
                footballPlayerService.save("Marc Guehi", 24, 0, crystalPalaceId, 40, 79, 10, "CB", 86);
                footballPlayerService.save("Maxence Lacroix", 24, 0, crystalPalaceId, 35, 72, 10, "CB", 82);
                footballPlayerService.save("Daniel Muñoz", 28, 0, crystalPalaceId, 68, 77, 10, "RB", 79);
                footballPlayerService.save("Tyrick Mitchell", 24, 0, crystalPalaceId, 55, 76, 10, "LB", 78);
                footballPlayerService.save("Adam Wharton", 20, 0, crystalPalaceId, 70, 86, 10, "CM", 74);
                footballPlayerService.save("Cheick Doucoure", 24, 0, crystalPalaceId, 65, 81, 10, "CDM", 83);
                footballPlayerService.save("Eberechi Eze", 26, 0, crystalPalaceId, 85, 88, 10, "CAM", 42);
                footballPlayerService.save("Daichi Kamada", 27, 0, crystalPalaceId, 78, 83, 10, "CAM", 55);
                footballPlayerService.save("Ismaila Sarr", 26, 0, crystalPalaceId, 80, 76, 10, "RW", 36);
                footballPlayerService.save("Jean-Philippe Mateta", 27, 0, crystalPalaceId, 86, 68, 10, "ST", 40);
                footballPlayerService.save("Eddie Nketiah", 25, 0, crystalPalaceId, 82, 70, 10, "ST", 35);
                footballPlayerService.save("Jefferson Lerma", 29, 0, crystalPalaceId, 72, 78, 10, "CDM", 81);
                footballPlayerService.save("Will Hughes", 29, 0, crystalPalaceId, 68, 81, 10, "CM", 76);
                footballPlayerService.save("Trevor Chalobah", 25, 0, crystalPalaceId, 45, 76, 10, "CB", 80);
                footballPlayerService.save("Chris Richards", 24, 0, crystalPalaceId, 35, 74, 10, "CB", 81);
                footballPlayerService.save("Matheus Franca", 20, 0, crystalPalaceId, 74, 78, 10, "CAM", 44);
                footballPlayerService.save("Matt Turner", 30, 0, crystalPalaceId, 10, 65, 77, "GK", 12);
            }

            int bournemouthId = teamService.getOrSave("Bournemouth", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(bournemouthId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Kepa Arrizabalaga", 29, 0, bournemouthId, 15, 78, 81, "GK", 12);
                footballPlayerService.save("Illia Zabarnyi", 21, 0, bournemouthId, 30, 75, 10, "CB", 83);
                footballPlayerService.save("Marcos Senesi", 27, 0, bournemouthId, 55, 80, 10, "CB", 81);
                footballPlayerService.save("Julian Araujo", 22, 0, bournemouthId, 60, 76, 10, "RB", 74);
                footballPlayerService.save("Milos Kerkez", 20, 0, bournemouthId, 62, 78, 10, "LB", 72);
                footballPlayerService.save("Lewis Cook", 27, 0, bournemouthId, 70, 84, 10, "CM", 78);
                footballPlayerService.save("Ryan Christie", 29, 0, bournemouthId, 75, 82, 10, "CM", 75);
                footballPlayerService.save("Justin Kluivert", 25, 0, bournemouthId, 80, 78, 10, "CAM", 44);
                footballPlayerService.save("Antoine Semenyo", 24, 0, bournemouthId, 82, 75, 10, "RW", 48);
                footballPlayerService.save("Luis Sinisterra", 25, 0, bournemouthId, 81, 77, 10, "LW", 35);
                footballPlayerService.save("Evanilson", 24, 0, bournemouthId, 84, 72, 10, "ST", 40);
                footballPlayerService.save("Enes Ünal", 27, 0, bournemouthId, 80, 74, 10, "ST", 42);
                footballPlayerService.save("Marcus Tavernier", 25, 0, bournemouthId, 78, 81, 10, "LW", 52);
                footballPlayerService.save("Tyler Adams", 25, 0, bournemouthId, 60, 80, 10, "CDM", 84);
                footballPlayerService.save("Alex Scott", 20, 0, bournemouthId, 72, 82, 10, "CM", 65);
                footballPlayerService.save("Dean Huijsen", 19, 0, bournemouthId, 45, 78, 10, "CB", 79);
                footballPlayerService.save("Dango Ouattara", 22, 0, bournemouthId, 77, 75, 10, "RW", 46);
                footballPlayerService.save("Mark Travers", 25, 0, bournemouthId, 10, 60, 77, "GK", 11);
            }

            int evertonId = teamService.getOrSave("Everton", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(evertonId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Jordan Pickford", 30, 0, evertonId, 25, 84, 84, "GK", 15);
                footballPlayerService.save("James Tarkowski", 31, 0, evertonId, 45, 72, 10, "CB", 86);
                footballPlayerService.save("Jarrad Branthwaite", 22, 0, evertonId, 35, 76, 10, "CB", 85);
                footballPlayerService.save("Nathan Patterson", 22, 0, evertonId, 58, 75, 10, "RB", 74);
                footballPlayerService.save("Vitaliy Mykolenko", 25, 0, evertonId, 55, 76, 10, "LB", 79);
                footballPlayerService.save("Idrissa Gueye", 34, 0, evertonId, 65, 80, 10, "CDM", 84);
                footballPlayerService.save("Abdoulaye Doucoure", 31, 0, evertonId, 78, 77, 10, "CM", 72);
                footballPlayerService.save("Dwight McNeil", 24, 0, evertonId, 80, 83, 10, "CAM", 58);
                footballPlayerService.save("Iliman Ndiaye", 24, 0, evertonId, 79, 81, 10, "LW", 45);
                footballPlayerService.save("Jack Harrison", 27, 0, evertonId, 77, 79, 10, "RW", 54);
                footballPlayerService.save("Dominic Calvert-Lewin", 27, 0, evertonId, 82, 68, 10, "ST", 48);
                footballPlayerService.save("Beto", 26, 0, evertonId, 78, 62, 10, "ST", 35);
                footballPlayerService.save("Orel Mangala", 26, 0, evertonId, 65, 82, 10, "CDM", 79);
                footballPlayerService.save("James Garner", 23, 0, evertonId, 74, 82, 10, "CM", 76);
                footballPlayerService.save("Tim Iroegbunam", 21, 0, evertonId, 64, 78, 10, "CM", 72);
                footballPlayerService.save("Jake O'Brien", 23, 0, evertonId, 35, 70, 10, "CB", 80);
                footballPlayerService.save("Ashley Young", 39, 0, evertonId, 68, 80, 10, "RB", 75);
                footballPlayerService.save("João Virgínia", 24, 0, evertonId, 10, 62, 74, "GK", 12);
            }


            int wolvesId = teamService.getOrSave("Wolverhampton Wanderers", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(wolvesId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Jose Sa", 31, 0, wolvesId, 15, 68, 82, "GK", 14);
                footballPlayerService.save("Yerson Mosquera", 23, 0, wolvesId, 30, 72, 10, "CB", 81);
                footballPlayerService.save("Craig Dawson", 34, 0, wolvesId, 45, 70, 10, "CB", 84);
                footballPlayerService.save("Nelson Semedo", 30, 0, wolvesId, 60, 78, 10, "RB", 78);
                footballPlayerService.save("Rayan Ait-Nouri", 23, 0, wolvesId, 74, 81, 10, "LB", 72);
                footballPlayerService.save("Joao Gomes", 23, 0, wolvesId, 68, 82, 10, "CM", 83);
                footballPlayerService.save("Andre", 23, 0, wolvesId, 65, 84, 10, "CDM", 82);
                footballPlayerService.save("Mario Lemina", 30, 0, wolvesId, 72, 80, 10, "CM", 79);
                footballPlayerService.save("Matheus Cunha", 25, 0, wolvesId, 84, 83, 10, "CAM", 45);
                footballPlayerService.save("Hwang Hee-chan", 28, 0, wolvesId, 83, 75, 10, "RW", 42);
                footballPlayerService.save("Jorgen Strand Larsen", 24, 0, wolvesId, 82, 68, 10, "ST", 44);
                footballPlayerService.save("Goncalo Guedes", 27, 0, wolvesId, 80, 77, 10, "ST", 35);
                footballPlayerService.save("Jean-Ricner Bellegarde", 26, 0, wolvesId, 74, 79, 10, "CM", 64);
                footballPlayerService.save("Tommy Doyle", 22, 0, wolvesId, 70, 83, 10, "CM", 68);
                footballPlayerService.save("Toti Gomes", 25, 0, wolvesId, 35, 74, 10, "CB", 80);
                footballPlayerService.save("Matt Doherty", 32, 0, wolvesId, 65, 76, 10, "RB", 74);
                footballPlayerService.save("Rodrigo Gomes", 21, 0, wolvesId, 75, 78, 10, "RW", 40);
                footballPlayerService.save("Sam Johnstone", 31, 0, wolvesId, 10, 65, 79, "GK", 12);
            }


            int forestId = teamService.getOrSave("Nottingham Forest", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(forestId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Matz Sels", 32, 0, forestId, 15, 68, 80, "GK", 14);
                footballPlayerService.save("Murillo", 22, 0, forestId, 45, 80, 10, "CB", 85);
                footballPlayerService.save("Nikola Milenkovic", 26, 0, forestId, 35, 74, 10, "CB", 83);
                footballPlayerService.save("Ola Aina", 27, 0, forestId, 65, 77, 10, "RB", 78);
                footballPlayerService.save("Alex Moreno", 31, 0, forestId, 68, 79, 10, "LB", 75);
                footballPlayerService.save("Ibrahim Sangare", 26, 0, forestId, 62, 81, 10, "CDM", 84);
                footballPlayerService.save("Elliot Anderson", 21, 0, forestId, 74, 82, 10, "CM", 70);
                footballPlayerService.save("Morgan Gibbs-White", 24, 0, forestId, 81, 86, 10, "CAM", 46);
                footballPlayerService.save("Callum Hudson-Odoi", 23, 0, forestId, 80, 78, 10, "LW", 35);
                footballPlayerService.save("Anthony Elanga", 22, 0, forestId, 78, 76, 10, "RW", 40);
                footballPlayerService.save("Chris Wood", 32, 0, forestId, 84, 65, 10, "ST", 48);
                footballPlayerService.save("Taiwo Awoniyi", 26, 0, forestId, 82, 68, 10, "ST", 42);
                footballPlayerService.save("James Ward-Prowse", 29, 0, forestId, 82, 90, 10, "CM", 65);
                footballPlayerService.save("Nicolas Dominguez", 26, 0, forestId, 72, 82, 10, "CM", 78);
                footballPlayerService.save("Morato", 23, 0, forestId, 30, 72, 10, "CB", 81);
                footballPlayerService.save("Neco Williams", 23, 0, forestId, 64, 78, 10, "RB", 72);
                footballPlayerService.save("Ramon Sosa", 24, 0, forestId, 77, 75, 10, "LW", 38);
                footballPlayerService.save("Carlos Miguel", 25, 0, forestId, 10, 60, 78, "GK", 12);

            }

            int leicesterId = teamService.getOrSave("Leicester City", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(leicesterId)) {
                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Mads Hermansen", 23, 0, leicesterId, 15, 82, 80, "GK", 14);
                footballPlayerService.save("Wout Faes", 26, 0, leicesterId, 35, 75, 10, "CB", 82);
                footballPlayerService.save("Caleb Okoli", 23, 0, leicesterId, 25, 68, 10, "CB", 80);
                footballPlayerService.save("James Justin", 26, 0, leicesterId, 62, 77, 10, "RB", 76);
                footballPlayerService.save("Victor Kristiansen", 21, 0, leicesterId, 58, 76, 10, "LB", 75);
                footballPlayerService.save("Harry Winks", 28, 0, leicesterId, 65, 86, 10, "CDM", 74);
                footballPlayerService.save("Wilfred Ndidi", 27, 0, leicesterId, 62, 78, 10, "CM", 81);
                footballPlayerService.save("Facundo Buonanotte", 19, 0, leicesterId, 79, 82, 10, "CAM", 45);
                footballPlayerService.save("Stephy Mavididi", 26, 0, leicesterId, 80, 75, 10, "LW", 42);
                footballPlayerService.save("Abdul Fatawu", 20, 0, leicesterId, 78, 77, 10, "RW", 48);
                footballPlayerService.save("Jamie Vardy", 37, 0, leicesterId, 85, 68, 10, "ST", 40);
                footballPlayerService.save("Odsonne Edouard", 26, 0, leicesterId, 80, 72, 10, "ST", 35);
                footballPlayerService.save("Jordan Ayew", 32, 0, leicesterId, 76, 78, 10, "RW", 60);
                footballPlayerService.save("Oliver Skipp", 23, 0, leicesterId, 60, 81, 10, "CDM", 82);
                footballPlayerService.save("Boubakary Soumare", 25, 0, leicesterId, 65, 80, 10, "CM", 76);
                footballPlayerService.save("Jannik Vestergaard", 31, 0, leicesterId, 50, 79, 10, "CB", 81);
                footballPlayerService.save("Ricardo Pereira", 30, 0, leicesterId, 68, 82, 10, "RB", 78);
                footballPlayerService.save("Danny Ward", 31, 0, leicesterId, 10, 65, 76, "GK", 12);
            }

            int ipswichId = teamService.getOrSave("Ipswich Town", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(ipswichId)) {
                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Arijanet Muric", 25, 0, ipswichId, 15, 78, 79, "GK", 13);
                footballPlayerService.save("Jacob Greaves", 23, 0, ipswichId, 30, 72, 10, "CB", 82);
                footballPlayerService.save("Dara O'Shea", 25, 0, ipswichId, 35, 74, 10, "CB", 81);
                footballPlayerService.save("Ben Johnson", 24, 0, ipswichId, 58, 75, 10, "RB", 77);
                footballPlayerService.save("Leif Davis", 24, 0, ipswichId, 65, 84, 10, "LB", 70);
                footballPlayerService.save("Kalvin Phillips", 28, 0, ipswichId, 68, 82, 10, "CDM", 80);
                footballPlayerService.save("Sam Morsy", 32, 0, ipswichId, 70, 78, 10, "CM", 76);
                footballPlayerService.save("Omari Hutchinson", 20, 0, ipswichId, 78, 80, 10, "CAM", 48);
                footballPlayerService.save("Sammie Szmodics", 28, 0, ipswichId, 82, 78, 10, "LW", 45);
                footballPlayerService.save("Chiedozie Ogbene", 27, 0, ipswichId, 77, 75, 10, "RW", 52);
                footballPlayerService.save("Liam Delap", 21, 0, ipswichId, 81, 65, 10, "ST", 38);
                footballPlayerService.save("George Hirst", 25, 0, ipswichId, 78, 62, 10, "ST", 42);
                footballPlayerService.save("Jack Clarke", 23, 0, ipswichId, 79, 80, 10, "LW", 35);
                footballPlayerService.save("Jens Cajuste", 24, 0, ipswichId, 65, 81, 10, "CM", 75);
                footballPlayerService.save("Conor Chaplin", 27, 0, ipswichId, 79, 77, 10, "CAM", 44);
                footballPlayerService.save("Luke Woolfenden", 25, 0, ipswichId, 25, 70, 10, "CB", 78);
                footballPlayerService.save("Axel Tuanzebe", 26, 0, ipswichId, 30, 72, 10, "RB", 79);
                footballPlayerService.save("Christian Walton", 28, 0, ipswichId, 10, 60, 75, "GK", 12);
            }

            int southamptonId = teamService.getOrSave("Southampton", premierLeague, footballId);

            if (!footballPlayerRepo.hasPlayers(southamptonId)) {

                // Parameters: Name, Age, Goals, TeamId, MarketValue, OVR, Technical/Reflex, Position, DEFENSE
                footballPlayerService.save("Aaron Ramsdale", 26, 0, southamptonId, 15, 78, 82, "GK", 15);
                footballPlayerService.save("Jan Bednarek", 28, 0, southamptonId, 35, 72, 10, "CB", 82);
                footballPlayerService.save("Taylor Harwood-Bellis", 22, 0, southamptonId, 30, 78, 10, "CB", 80);
                footballPlayerService.save("Yukinari Sugawara", 24, 0, southamptonId, 70, 81, 10, "RB", 76);
                footballPlayerService.save("Kyle Walker-Peters", 27, 0, southamptonId, 65, 80, 10, "LB", 78);
                footballPlayerService.save("Flynn Downes", 25, 0, southamptonId, 62, 82, 10, "CDM", 81);
                footballPlayerService.save("Mateus Fernandes", 19, 0, southamptonId, 74, 83, 10, "CM", 68);
                footballPlayerService.save("Adam Lallana", 36, 0, southamptonId, 76, 85, 10, "CAM", 54);
                footballPlayerService.save("Tyler Dibling", 18, 0, southamptonId, 78, 77, 10, "RW", 38);
                footballPlayerService.save("Ben Brereton Diaz", 25, 0, southamptonId, 80, 72, 10, "LW", 42);
                footballPlayerService.save("Cameron Archer", 22, 0, southamptonId, 81, 68, 10, "ST", 35);
                footballPlayerService.save("Adam Armstrong", 27, 0, southamptonId, 79, 74, 10, "ST", 44);
                footballPlayerService.save("Joe Aribo", 27, 0, southamptonId, 75, 79, 10, "CM", 72);
                footballPlayerService.save("Ryan Manning", 28, 0, southamptonId, 62, 81, 10, "LB", 74);
                footballPlayerService.save("Jack Stephens", 30, 0, southamptonId, 40, 75, 10, "CB", 79);
                footballPlayerService.save("Will Smallbone", 24, 0, southamptonId, 72, 82, 10, "CM", 70);
                footballPlayerService.save("Paul Onuachu", 30, 0, southamptonId, 82, 60, 10, "ST", 40);
                footballPlayerService.save("Alex McCarthy", 34, 0, southamptonId, 10, 60, 77, "GK", 12);
            }

            //for basketball----------------------------------------------

            BasketballPlayerRepo basketballPlayerRepo = new BasketballPlayerRepo(connection);

            BasketballPlayerService basketballPlayerService = new BasketballPlayerService(basketballPlayerRepo);

            int houstonId = teamService.getOrSave("Houston Rockets", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(houstonId)) {

                basketballPlayerService.addBasketPlayer("Alperen Şengün", 21, 0, houstonId, "C", 78, 84, 88, 85, 75, 65, 70);
                basketballPlayerService.addBasketPlayer("Jalen Green", 22, 0, houstonId, "SG", 82, 86, 75, 88, 70, 60, 55);
                basketballPlayerService.addBasketPlayer("Fred VanVleet", 30, 0, houstonId, "PG", 85, 82, 88, 78, 82, 85, 45);
                basketballPlayerService.addBasketPlayer("Jabari Smith Jr.", 20, 0, houstonId, "PF", 80, 72, 70, 78, 82, 65, 75);
                basketballPlayerService.addBasketPlayer("Dillon Brooks", 28, 0, houstonId, "SF", 76, 75, 74, 76, 88, 78, 50);
                // Bench (Yedekler)
                basketballPlayerService.addBasketPlayer("Amen Thompson", 21, 0, houstonId, "SG", 65, 85, 80, 85, 85, 82, 65);
                basketballPlayerService.addBasketPlayer("Cam Whitmore", 19, 0, houstonId, "SF", 82, 78, 65, 88, 72, 68, 55);
                basketballPlayerService.addBasketPlayer("Tari Eason", 22, 0, houstonId, "PF", 75, 74, 70, 80, 88, 88, 72);
                basketballPlayerService.addBasketPlayer("Reed Sheppard", 20, 0, houstonId, "PG", 88, 80, 82, 74, 75, 80, 45);
                basketballPlayerService.addBasketPlayer("Steven Adams", 30, 0, houstonId, "C", 40, 60, 78, 82, 84, 60, 70);
                basketballPlayerService.addBasketPlayer("Aaron Holiday", 27, 0, houstonId, "PG", 82, 78, 76, 72, 78, 72, 35);
                basketballPlayerService.addBasketPlayer("Jeff Green", 37, 0, houstonId, "PF", 78, 72, 74, 80, 74, 65, 60);
            }

            int gswId = teamService.getOrSave("Golden State Warriors", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(gswId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Stephen Curry", 36, 0, gswId, "PG", 98, 94, 92, 88, 72, 75, 40);
                basketballPlayerService.addBasketPlayer("Draymond Green", 34, 0, gswId, "PF", 72, 78, 88, 74, 94, 82, 85);
                basketballPlayerService.addBasketPlayer("Andrew Wiggins", 29, 0, gswId, "SF", 78, 79, 72, 82, 85, 74, 70);
                basketballPlayerService.addBasketPlayer("Brandin Podziemski", 21, 0, gswId, "SG", 80, 78, 82, 76, 75, 68, 45);
                basketballPlayerService.addBasketPlayer("Trayce Jackson-Davis", 24, 0, gswId, "C", 50, 65, 72, 85, 82, 65, 88);
                // Bench (Yedekler)
                basketballPlayerService.addBasketPlayer("Jonathan Kuminga", 21, 0, gswId, "PF", 75, 80, 72, 88, 80, 70, 72);
                basketballPlayerService.addBasketPlayer("Buddy Hield", 31, 0, gswId, "SG", 90, 76, 72, 74, 70, 68, 40);
                basketballPlayerService.addBasketPlayer("Kyle Anderson", 30, 0, gswId, "PF", 74, 82, 86, 76, 85, 80, 75);
                basketballPlayerService.addBasketPlayer("De'Anthony Melton", 26, 0, gswId, "PG", 82, 78, 78, 74, 88, 86, 60);
                basketballPlayerService.addBasketPlayer("Gary Payton II", 31, 0, gswId, "SG", 70, 74, 72, 84, 92, 90, 65);
                basketballPlayerService.addBasketPlayer("Kevon Looney", 28, 0, gswId, "C", 45, 62, 75, 78, 84, 62, 74);
                basketballPlayerService.addBasketPlayer("Moses Moody", 22, 0, gswId, "SF", 82, 74, 70, 76, 78, 70, 55);
            }


            int lakersId = teamService.getOrSave("Los Angeles Lakers", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(lakersId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("LeBron James", 39, 0, lakersId, "SF", 84, 88, 94, 92, 78, 75, 70);
                basketballPlayerService.addBasketPlayer("Anthony Davis", 31, 0, lakersId, "C", 78, 76, 74, 88, 96, 78, 94);
                basketballPlayerService.addBasketPlayer("Austin Reaves", 25, 0, lakersId, "SG", 82, 82, 84, 80, 74, 68, 45);
                basketballPlayerService.addBasketPlayer("D'Angelo Russell", 28, 0, lakersId, "PG", 86, 84, 85, 76, 70, 72, 40);
                basketballPlayerService.addBasketPlayer("Rui Hachimura", 26, 0, lakersId, "PF", 80, 74, 72, 82, 76, 62, 65);
                // Bench (Yedekler)
                basketballPlayerService.addBasketPlayer("Dalton Knecht", 23, 0, lakersId, "SG", 85, 76, 72, 78, 72, 65, 45);
                basketballPlayerService.addBasketPlayer("Gabe Vincent", 27, 0, lakersId, "PG", 80, 78, 76, 74, 84, 78, 35);
                basketballPlayerService.addBasketPlayer("Jarred Vanderbilt", 25, 0, lakersId, "PF", 60, 70, 72, 78, 92, 85, 70);
                basketballPlayerService.addBasketPlayer("Max Christie", 21, 0, lakersId, "SG", 78, 75, 72, 74, 80, 70, 55);
                basketballPlayerService.addBasketPlayer("Christian Wood", 28, 0, lakersId, "C", 82, 74, 68, 80, 70, 60, 75);
                basketballPlayerService.addBasketPlayer("Jaxson Hayes", 24, 0, lakersId, "C", 45, 65, 60, 88, 78, 62, 82);
                basketballPlayerService.addBasketPlayer("Cam Reddish", 24, 0, lakersId, "SF", 76, 78, 72, 78, 82, 76, 50);
            }


            int bucksId = teamService.getOrSave("Milwaukee Bucks", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(bucksId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Giannis Antetokounmpo", 29, 0, bucksId, "PF", 72, 86, 84, 96, 92, 78, 88);
                basketballPlayerService.addBasketPlayer("Damian Lillard", 33, 0, bucksId, "PG", 90, 92, 88, 85, 70, 72, 35);
                basketballPlayerService.addBasketPlayer("Brook Lopez", 36, 0, bucksId, "C", 82, 60, 65, 78, 88, 55, 94);
                basketballPlayerService.addBasketPlayer("Khris Middleton", 32, 0, bucksId, "SF", 85, 80, 82, 80, 78, 70, 50);
                basketballPlayerService.addBasketPlayer("Gary Trent Jr.", 25, 0, bucksId, "SG", 86, 78, 72, 76, 82, 84, 35);
                // Bench (Yedekler)
                basketballPlayerService.addBasketPlayer("Bobby Portis", 29, 0, bucksId, "PF", 82, 74, 70, 84, 76, 68, 65);
                basketballPlayerService.addBasketPlayer("Delon Wright", 32, 0, bucksId, "PG", 78, 80, 82, 74, 88, 88, 45);
                basketballPlayerService.addBasketPlayer("Taurean Prince", 30, 0, bucksId, "SF", 82, 75, 72, 76, 80, 72, 55);
                basketballPlayerService.addBasketPlayer("Pat Connaughton", 31, 0, bucksId, "SG", 80, 74, 75, 78, 76, 68, 50);
                basketballPlayerService.addBasketPlayer("Andre Jackson Jr.", 22, 0, bucksId, "SG", 68, 78, 76, 82, 85, 80, 60);
                basketballPlayerService.addBasketPlayer("A.J. Green", 24, 0, bucksId, "SG", 88, 72, 70, 68, 74, 62, 35);
                basketballPlayerService.addBasketPlayer("MarJon Beauchamp", 23, 0, bucksId, "SF", 76, 76, 72, 80, 78, 72, 55);
            }


            int dallasId = teamService.getOrSave("Dallas Mavericks", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(dallasId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Luka Doncic", 25, 0, dallasId, "PG", 92, 96, 98, 90, 78, 82, 50);
                basketballPlayerService.addBasketPlayer("Kyrie Irving", 32, 0, dallasId, "SG", 94, 98, 88, 92, 75, 74, 40);
                basketballPlayerService.addBasketPlayer("Dereck Lively II", 20, 0, dallasId, "C", 50, 68, 72, 88, 86, 65, 90);
                basketballPlayerService.addBasketPlayer("P.J. Washington", 25, 0, dallasId, "PF", 82, 75, 74, 82, 85, 78, 78);
                basketballPlayerService.addBasketPlayer("Klay Thompson", 34, 0, dallasId, "SF", 88, 72, 74, 75, 78, 70, 50);
                // Bench
                basketballPlayerService.addBasketPlayer("Daniel Gafford", 25, 0, dallasId, "C", 40, 60, 62, 90, 84, 60, 88);
                basketballPlayerService.addBasketPlayer("Naji Marshall", 26, 0, dallasId, "SF", 78, 77, 76, 80, 86, 80, 55);
                basketballPlayerService.addBasketPlayer("Quentin Grimes", 24, 0, dallasId, "SG", 84, 76, 74, 78, 84, 78, 45);
                basketballPlayerService.addBasketPlayer("Jaden Hardy", 21, 0, dallasId, "SG", 84, 85, 76, 80, 72, 65, 35);
                basketballPlayerService.addBasketPlayer("Maxi Kleber", 32, 0, dallasId, "PF", 80, 70, 72, 74, 82, 68, 78);
                basketballPlayerService.addBasketPlayer("Dante Exum", 28, 0, dallasId, "PG", 82, 84, 85, 80, 84, 76, 45);
                basketballPlayerService.addBasketPlayer("Dwight Powell", 32, 0, dallasId, "C", 50, 62, 70, 82, 76, 64, 70);
            }


            int denverId = teamService.getOrSave("Denver Nuggets", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(denverId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Nikola Jokic", 29, 0, denverId, "C", 90, 88, 99, 94, 82, 84, 72);
                basketballPlayerService.addBasketPlayer("Jamal Murray", 27, 0, denverId, "PG", 88, 90, 86, 85, 76, 72, 45);
                basketballPlayerService.addBasketPlayer("Aaron Gordon", 28, 0, denverId, "PF", 74, 78, 80, 92, 90, 76, 80);
                basketballPlayerService.addBasketPlayer("Michael Porter Jr.", 25, 0, denverId, "SF", 90, 74, 70, 82, 75, 68, 72);
                basketballPlayerService.addBasketPlayer("Christian Braun", 23, 0, denverId, "SG", 78, 78, 75, 84, 86, 80, 62);
                // Bench
                basketballPlayerService.addBasketPlayer("Russell Westbrook", 35, 0, denverId, "PG", 74, 88, 84, 86, 80, 82, 50);
                basketballPlayerService.addBasketPlayer("Peyton Watson", 21, 0, denverId, "SF", 72, 76, 72, 82, 90, 78, 88);
                basketballPlayerService.addBasketPlayer("Dario Saric", 30, 0, denverId, "PF", 82, 76, 82, 78, 74, 65, 60);
                basketballPlayerService.addBasketPlayer("Julian Strawther", 22, 0, denverId, "SG", 84, 75, 72, 76, 72, 68, 40);
                basketballPlayerService.addBasketPlayer("Zeke Nnaji", 23, 0, denverId, "C", 76, 68, 65, 80, 78, 60, 75);
                basketballPlayerService.addBasketPlayer("DeAndre Jordan", 35, 0, denverId, "C", 30, 55, 60, 85, 75, 50, 80);
                basketballPlayerService.addBasketPlayer("Vlatko Cancar", 27, 0, denverId, "PF", 78, 72, 75, 76, 74, 62, 55);
            }


            int okcId = teamService.getOrSave("OKC Thunder", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(okcId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Shai Gilgeous-Alexander", 25, 0, okcId, "PG", 92, 96, 88, 94, 90, 92, 65);
                basketballPlayerService.addBasketPlayer("Chet Holmgren", 22, 0, okcId, "C", 84, 78, 80, 85, 92, 70, 96);
                basketballPlayerService.addBasketPlayer("Jalen Williams", 23, 0, okcId, "SF", 86, 85, 84, 88, 88, 82, 65);
                basketballPlayerService.addBasketPlayer("Alex Caruso", 30, 0, okcId, "SG", 82, 78, 80, 75, 96, 94, 70);
                basketballPlayerService.addBasketPlayer("Isaiah Hartenstein", 25, 0, okcId, "C", 55, 72, 82, 84, 88, 75, 82);
                // Bench
                basketballPlayerService.addBasketPlayer("Luguentz Dort", 25, 0, okcId, "SF", 82, 75, 72, 78, 95, 85, 55);
                basketballPlayerService.addBasketPlayer("Isaiah Joe", 24, 0, okcId, "SG", 92, 74, 72, 70, 76, 72, 35);
                basketballPlayerService.addBasketPlayer("Cason Wallace", 20, 0, okcId, "PG", 80, 80, 78, 76, 90, 84, 55);
                basketballPlayerService.addBasketPlayer("Aaron Wiggins", 25, 0, okcId, "SG", 84, 78, 75, 82, 82, 78, 50);
                basketballPlayerService.addBasketPlayer("Jaylin Williams", 21, 0, okcId, "C", 78, 72, 84, 75, 78, 68, 65);
                basketballPlayerService.addBasketPlayer("Kenrich Williams", 29, 0, okcId, "PF", 78, 74, 76, 76, 84, 76, 55);
                basketballPlayerService.addBasketPlayer("Ousmane Dieng", 21, 0, okcId, "PF", 75, 78, 76, 78, 78, 70, 68);
            }

            int phoenixId = teamService.getOrSave("Phoenix Suns", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(phoenixId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Kevin Durant", 35, 0, phoenixId, "PF", 96, 88, 84, 90, 82, 70, 85);
                basketballPlayerService.addBasketPlayer("Devin Booker", 27, 0, phoenixId, "SG", 94, 90, 88, 88, 78, 72, 45);
                basketballPlayerService.addBasketPlayer("Bradley Beal", 30, 0, phoenixId, "PG", 88, 88, 82, 86, 74, 70, 40);
                basketballPlayerService.addBasketPlayer("Tyus Jones", 28, 0, phoenixId, "PG", 84, 85, 94, 78, 78, 80, 30);
                basketballPlayerService.addBasketPlayer("Jusuf Nurkic", 29, 0, phoenixId, "C", 65, 74, 85, 82, 80, 74, 78);
                // Bench
                basketballPlayerService.addBasketPlayer("Grayson Allen", 28, 0, phoenixId, "SG", 92, 76, 78, 74, 80, 74, 40);
                basketballPlayerService.addBasketPlayer("Royce O'Neale", 30, 0, phoenixId, "SF", 82, 75, 78, 72, 85, 78, 55);
                basketballPlayerService.addBasketPlayer("Mason Plumlee", 34, 0, phoenixId, "C", 40, 70, 80, 82, 78, 62, 74);
                basketballPlayerService.addBasketPlayer("Monte Morris", 28, 0, phoenixId, "PG", 82, 82, 86, 74, 75, 72, 30);
                basketballPlayerService.addBasketPlayer("Josh Okogie", 25, 0, phoenixId, "SF", 68, 74, 70, 78, 92, 88, 65);
                basketballPlayerService.addBasketPlayer("Bol Bol", 24, 0, phoenixId, "C", 82, 78, 70, 80, 76, 60, 88);
                basketballPlayerService.addBasketPlayer("Damion Lee", 31, 0, phoenixId, "SG", 85, 74, 72, 70, 74, 68, 30);
            }


            int knicksId = teamService.getOrSave("New York Knicks", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(knicksId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Jalen Brunson", 27, 0, knicksId, "PG", 92, 94, 88, 92, 76, 72, 35);
                basketballPlayerService.addBasketPlayer("Karl-Anthony Towns", 28, 0, knicksId, "C", 90, 82, 78, 88, 80, 68, 82);
                basketballPlayerService.addBasketPlayer("Mikal Bridges", 27, 0, knicksId, "SF", 86, 82, 80, 82, 94, 88, 72);
                basketballPlayerService.addBasketPlayer("Josh Hart", 29, 0, knicksId, "SG", 80, 82, 84, 84, 88, 82, 55);
                basketballPlayerService.addBasketPlayer("OG Anunoby", 26, 0, knicksId, "PF", 84, 78, 74, 82, 98, 92, 78);
                // Bench
                basketballPlayerService.addBasketPlayer("Miles McBride", 23, 0, knicksId, "PG", 86, 82, 78, 76, 90, 84, 40);
                basketballPlayerService.addBasketPlayer("Mitchell Robinson", 26, 0, knicksId, "C", 30, 55, 58, 88, 92, 70, 94);
                basketballPlayerService.addBasketPlayer("Precious Achiuwa", 24, 0, knicksId, "PF", 72, 74, 70, 82, 84, 74, 78);
                basketballPlayerService.addBasketPlayer("Cameron Payne", 29, 0, knicksId, "PG", 82, 84, 82, 76, 74, 70, 35);
                basketballPlayerService.addBasketPlayer("Landry Shamet", 27, 0, knicksId, "SG", 85, 75, 72, 70, 72, 65, 30);
                basketballPlayerService.addBasketPlayer("Jericho Sims", 25, 0, knicksId, "C", 35, 62, 60, 84, 80, 60, 78);
                basketballPlayerService.addBasketPlayer("Kolek Tyler", 23, 0, knicksId, "PG", 78, 84, 88, 74, 72, 68, 30);
            }

            int spursId = teamService.getOrSave("San Antonio Spurs", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(spursId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Victor Wembanyama", 20, 0, spursId, "C", 84, 82, 80, 88, 92, 75, 99);
                basketballPlayerService.addBasketPlayer("Chris Paul", 39, 0, spursId, "PG", 82, 85, 96, 74, 80, 88, 30);
                basketballPlayerService.addBasketPlayer("Devin Vassell", 23, 0, spursId, "SG", 86, 80, 75, 82, 82, 78, 45);
                basketballPlayerService.addBasketPlayer("Jeremy Sochan", 21, 0, spursId, "PF", 72, 76, 78, 80, 88, 82, 60);
                basketballPlayerService.addBasketPlayer("Harrison Barnes", 32, 0, spursId, "SF", 84, 75, 72, 78, 76, 68, 40);
                // Bench
                basketballPlayerService.addBasketPlayer("Stephon Castle", 19, 0, spursId, "SG", 70, 82, 80, 84, 88, 84, 50);
                basketballPlayerService.addBasketPlayer("Keldon Johnson", 24, 0, spursId, "SF", 80, 74, 70, 86, 74, 65, 45);
                basketballPlayerService.addBasketPlayer("Zach Collins", 26, 0, spursId, "C", 78, 68, 74, 80, 78, 60, 75);
                basketballPlayerService.addBasketPlayer("Tre Jones", 24, 0, spursId, "PG", 76, 82, 88, 76, 78, 80, 30);
                basketballPlayerService.addBasketPlayer("Julian Champagnie", 23, 0, spursId, "SF", 84, 72, 70, 74, 76, 72, 45);
                basketballPlayerService.addBasketPlayer("Sandro Mamukelashvili", 25, 0, spursId, "PF", 78, 74, 76, 78, 72, 64, 55);
                basketballPlayerService.addBasketPlayer("Malaki Branham", 21, 0, spursId, "SG", 82, 78, 74, 76, 70, 62, 30);
            }

            int timberwolvesId = teamService.getOrSave("Minnesota Timberwolves", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(timberwolvesId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Anthony Edwards", 22, 0, timberwolvesId, "SG", 88, 92, 82, 94, 88, 84, 60);
                basketballPlayerService.addBasketPlayer("Julius Randle", 29, 0, timberwolvesId, "PF", 82, 80, 84, 88, 76, 65, 55);
                basketballPlayerService.addBasketPlayer("Rudy Gobert", 32, 0, timberwolvesId, "C", 30, 50, 60, 85, 95, 68, 96);
                basketballPlayerService.addBasketPlayer("Jaden McDaniels", 23, 0, timberwolvesId, "SF", 80, 74, 70, 82, 96, 85, 75);
                basketballPlayerService.addBasketPlayer("Mike Conley", 36, 0, timberwolvesId, "PG", 86, 80, 88, 72, 82, 84, 30);
                // Bench
                basketballPlayerService.addBasketPlayer("Donte DiVincenzo", 27, 0, timberwolvesId, "SG", 88, 78, 76, 78, 84, 82, 40);
                basketballPlayerService.addBasketPlayer("Naz Reid", 24, 0, timberwolvesId, "C", 85, 78, 72, 84, 78, 70, 74);
                basketballPlayerService.addBasketPlayer("Nickeil Alexander-Walker", 25, 0, timberwolvesId, "SG", 82, 76, 75, 74, 88, 85, 45);
                basketballPlayerService.addBasketPlayer("Rob Dillingham", 19, 0, timberwolvesId, "PG", 85, 88, 82, 80, 68, 65, 30);
                basketballPlayerService.addBasketPlayer("Joe Ingles", 36, 0, timberwolvesId, "SF", 84, 70, 86, 70, 74, 68, 35);
                basketballPlayerService.addBasketPlayer("Luka Garza", 25, 0, timberwolvesId, "C", 80, 65, 62, 82, 68, 55, 65);
                basketballPlayerService.addBasketPlayer("Terrence Shannon Jr.", 23, 0, timberwolvesId, "SF", 78, 84, 70, 85, 76, 72, 45);
            }

            int phillyId = teamService.getOrSave("Philadelphia 76ers", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(phillyId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Joel Embiid", 30, 0, phillyId, "C", 88, 84, 80, 92, 94, 75, 90);
                basketballPlayerService.addBasketPlayer("Tyrese Maxey", 23, 0, phillyId, "PG", 90, 96, 85, 90, 76, 82, 40);
                basketballPlayerService.addBasketPlayer("Paul George", 34, 0, phillyId, "SF", 92, 86, 82, 88, 90, 86, 60);
                basketballPlayerService.addBasketPlayer("Kelly Oubre Jr.", 28, 0, phillyId, "SG", 82, 82, 70, 86, 80, 78, 55);
                basketballPlayerService.addBasketPlayer("Caleb Martin", 28, 0, phillyId, "PF", 80, 78, 74, 78, 84, 80, 50);
                // Bench
                basketballPlayerService.addBasketPlayer("Kyle Lowry", 38, 0, phillyId, "PG", 82, 76, 88, 72, 80, 82, 30);
                basketballPlayerService.addBasketPlayer("Andre Drummond", 30, 0, phillyId, "C", 30, 55, 62, 85, 80, 68, 78);
                basketballPlayerService.addBasketPlayer("Eric Gordon", 35, 0, phillyId, "SG", 86, 74, 72, 76, 72, 65, 30);
                basketballPlayerService.addBasketPlayer("Guerschon Yabusele", 28, 0, phillyId, "PF", 78, 75, 74, 82, 76, 70, 60);
                basketballPlayerService.addBasketPlayer("Reggie Jackson", 34, 0, phillyId, "PG", 82, 84, 80, 78, 72, 68, 30);
                basketballPlayerService.addBasketPlayer("Ricky Council IV", 22, 0, phillyId, "SF", 75, 80, 68, 84, 78, 74, 50);
                basketballPlayerService.addBasketPlayer("Jared McCain", 20, 0, phillyId, "PG", 85, 78, 76, 74, 72, 65, 30);
            }

            int heatId = teamService.getOrSave("Miami Heat", nbaLeague, basketballId);

            if (!basketballPlayerRepo.hasPlayers(heatId)) {
                // İlk 5
                basketballPlayerService.addBasketPlayer("Jimmy Butler", 34, 0, heatId, "SF", 82, 86, 88, 90, 94, 92, 55);
                basketballPlayerService.addBasketPlayer("Bam Adebayo", 26, 0, heatId, "C", 68, 78, 84, 86, 96, 85, 88);
                basketballPlayerService.addBasketPlayer("Tyler Herro", 24, 0, heatId, "SG", 90, 88, 82, 85, 74, 70, 35);
                basketballPlayerService.addBasketPlayer("Terry Rozier", 30, 0, heatId, "PG", 85, 86, 82, 84, 76, 78, 40);
                basketballPlayerService.addBasketPlayer("Nikola Jovic", 21, 0, heatId, "PF", 82, 76, 78, 78, 75, 68, 55);
                // Bench
                basketballPlayerService.addBasketPlayer("Jaime Jaquez Jr.", 23, 0, heatId, "SF", 78, 82, 80, 84, 82, 78, 50);
                basketballPlayerService.addBasketPlayer("Duncan Robinson", 30, 0, heatId, "SG", 92, 72, 74, 70, 72, 65, 30);
                basketballPlayerService.addBasketPlayer("Kevin Love", 35, 0, heatId, "PF", 85, 68, 82, 76, 75, 62, 50);
                basketballPlayerService.addBasketPlayer("Haywood Highsmith", 27, 0, heatId, "SF", 80, 72, 70, 74, 90, 86, 65);
                basketballPlayerService.addBasketPlayer("Josh Richardson", 30, 0, heatId, "SG", 82, 78, 76, 74, 85, 82, 45);
                basketballPlayerService.addBasketPlayer("Kel'el Ware", 20, 0, heatId, "C", 72, 65, 62, 82, 80, 60, 85);
                basketballPlayerService.addBasketPlayer("Alec Burks", 32, 0, heatId, "SG", 84, 78, 74, 76, 72, 68, 30);
            }


        }
    }
}
