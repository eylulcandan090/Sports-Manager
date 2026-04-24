package DataFeed;

import Database.Database;
import Repository.CoachRepo;
import Repository.Football.FootballPlayerRepo;
import Repository.LeagueRepo;
import Repository.SportRepo;
import Repository.TeamRepo;
import Service.FootballPlayerService;
import Service.LeagueService;
import Service.SportService;
import Service.TeamService;

import java.sql.Connection;

public class DataFeed {

    public static void feed(){
        Database database=Database.getInstance();
        Connection connection=database.getConnection();
        CoachRepo coachRepo=new CoachRepo(connection);

        FootballPlayerRepo footballPlayerRepo=new FootballPlayerRepo(connection);
        FootballPlayerService footballPlayerService=new FootballPlayerService(footballPlayerRepo,coachRepo);

        SportRepo sportRepo=new SportRepo(connection);
        SportService sportService=new SportService(sportRepo);

        TeamRepo teamRepo=new TeamRepo(connection);
        TeamService teamService=new TeamService(teamRepo);


        LeagueRepo leagueRepo=new LeagueRepo(connection);
        LeagueService leagueService=new LeagueService(leagueRepo);


        int footballId=sportService.getOrSave("Football");
        int basketballId=sportService.getOrSave("Basketball");

        int premierLeague=leagueService.getOrSave("Premier League",footballId);
        int nbaLeague=leagueService.getOrSave("NBA",basketballId);


        int arsenalId=teamService.getOrSave("Arsenal",premierLeague,footballId);

        System.out.println("footballId = " + footballId);
        System.out.println("basketballId = " + basketballId);
        System.out.println("premierLeague = " + premierLeague);
        System.out.println("arsenalId = " + arsenalId);


        if(!footballPlayerRepo.hasPlayers(arsenalId)) {
            System.out.println("hmmmmm");

            footballPlayerService.save("David Raya", 28, 0, arsenalId, 20, 75, 88, "GK"); // Arsenal
            footballPlayerService.save("William Saliba", 23, 0, arsenalId, 35, 82, 10, "CB"); // Arsenal
            footballPlayerService.save("Gabriel Magalhães", 26, 0, arsenalId, 40, 78, 10, "CB"); // Arsenal
            footballPlayerService.save("Ben White", 26, 0, arsenalId, 45, 81, 10, "RB"); // Arsenal
            footballPlayerService.save("Riccardo Calafiori", 22, 0, arsenalId, 50, 80, 10, "LB"); // Arsenal
            footballPlayerService.save("Declan Rice", 25, 0, arsenalId, 68, 86, 10, "CDM"); // Arsenal
            footballPlayerService.save("Martin Ødegaard", 25, 0, arsenalId, 82, 92, 10, "CAM"); // Arsenal
            footballPlayerService.save("Mikel Merino", 28, 0, arsenalId, 70, 84, 10, "CM"); // Arsenal
            footballPlayerService.save("Bukayo Saka", 22, 0, arsenalId, 85, 85, 10, "RW"); // Arsenal
            footballPlayerService.save("Gabriel Martinelli", 23, 0, arsenalId, 80, 78, 10, "LW"); // Arsenal
            footballPlayerService.save("Kai Havertz", 25, 0, arsenalId, 82, 80, 10, "ST"); // Arsenal
            footballPlayerService.save("Leandro Trossard", 29, 0, arsenalId, 84, 82, 10, "LW"); // Arsenal
            footballPlayerService.save("Jurriën Timber", 23, 0, arsenalId, 40, 83, 10, "RB"); // Arsenal
            footballPlayerService.save("Oleksandr Zinchenko", 27, 0, arsenalId, 65, 88, 10, "LB"); // Arsenal
            footballPlayerService.save("Thomas Partey", 31, 0, arsenalId, 72, 84, 10, "CDM"); // Arsenal
            footballPlayerService.save("Gabriel Jesus", 27, 0, arsenalId, 80, 79, 10, "ST"); // Arsenal
            footballPlayerService.save("Jorginho", 32, 0, arsenalId, 65, 90, 10, "CM"); // Arsenal
            footballPlayerService.save("Neto", 35, 0, arsenalId, 15, 65, 80, "GK"); // Arsenal
        }


        int manCityId = teamService.getOrSave("Manchester City", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(manCityId)) {

            footballPlayerService.save("Ederson", 30, 0, manCityId, 20, 93, 89, "GK"); // Manchester City
            footballPlayerService.save("Ruben Dias", 27, 0, manCityId, 38, 80, 10, "CB"); // Manchester City
            footballPlayerService.save("John Stones", 30, 0, manCityId, 50, 88, 10, "CB"); // Manchester City
            footballPlayerService.save("Kyle Walker", 34, 0, manCityId, 55, 78, 10, "RB"); // Manchester City
            footballPlayerService.save("Josko Gvardiol", 22, 0, manCityId, 65, 84, 10, "LB"); // Manchester City
            footballPlayerService.save("Rodri", 28, 0, manCityId, 80, 91, 10, "CDM"); // Manchester City
            footballPlayerService.save("Kevin De Bruyne", 33, 0, manCityId, 88, 94, 10, "CAM"); // Manchester City
            footballPlayerService.save("Bernardo Silva", 29, 0, manCityId, 78, 90, 10, "CM"); // Manchester City
            footballPlayerService.save("Phil Foden", 24, 0, manCityId, 86, 88, 10, "RW"); // Manchester City
            footballPlayerService.save("Jack Grealish", 28, 0, manCityId, 76, 86, 10, "LW"); // Manchester City
            footballPlayerService.save("Erling Haaland", 23, 0, manCityId, 95, 65, 10, "ST"); // Manchester City
            footballPlayerService.save("Mateo Kovacic", 30, 0, manCityId, 68, 88, 10, "CM"); // Manchester City
            footballPlayerService.save("Jeremy Doku", 22, 0, manCityId, 78, 75, 10, "LW"); // Manchester City
            footballPlayerService.save("Manuel Akanji", 28, 0, manCityId, 45, 83, 10, "CB"); // Manchester City
            footballPlayerService.save("Nathan Ake", 29, 0, manCityId, 55, 81, 10, "CB"); // Manchester City
            footballPlayerService.save("Ilkay Gündogan", 33, 0, manCityId, 82, 89, 10, "CM"); // Manchester City
            footballPlayerService.save("Savinho", 20, 0, manCityId, 77, 80, 10, "RW"); // Manchester City
            footballPlayerService.save("Stefan Ortega", 31, 0, manCityId, 15, 82, 82, "GK"); // Manchester City

        }

        int liverpoolId = teamService.getOrSave("Liverpool", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(liverpoolId)) {

            footballPlayerService.save("Alisson Becker", 31, 0, liverpoolId, 15, 78, 90, "GK"); // Liverpool
            footballPlayerService.save("Virgil van Dijk", 32, 0, liverpoolId, 60, 81, 10, "CB"); // Liverpool
            footballPlayerService.save("Ibrahima Konate", 25, 0, liverpoolId, 30, 70, 10, "CB"); // Liverpool
            footballPlayerService.save("Trent Alexander-Arnold", 25, 0, liverpoolId, 75, 92, 10, "RB"); // Liverpool
            footballPlayerService.save("Andrew Robertson", 30, 0, liverpoolId, 62, 84, 10, "LB"); // Liverpool
            footballPlayerService.save("Alexis Mac Allister", 25, 0, liverpoolId, 80, 88, 10, "CM"); // Liverpool
            footballPlayerService.save("Dominik Szoboszlai", 23, 0, liverpoolId, 84, 87, 10, "CM"); // Liverpool
            footballPlayerService.save("Ryan Gravenberch", 22, 0, liverpoolId, 74, 83, 10, "CDM"); // Liverpool
            footballPlayerService.save("Mohamed Salah", 32, 0, liverpoolId, 91, 84, 10, "RW"); // Liverpool
            footballPlayerService.save("Luis Diaz", 27, 0, liverpoolId, 82, 78, 10, "LW"); // Liverpool
            footballPlayerService.save("Diogo Jota", 27, 0, liverpoolId, 85, 77, 10, "ST"); // Liverpool
            footballPlayerService.save("Darwin Nuñez", 25, 0, liverpoolId, 81, 72, 10, "ST"); // Liverpool
            footballPlayerService.save("Cody Gakpo", 25, 0, liverpoolId, 83, 81, 10, "LW"); // Liverpool
            footballPlayerService.save("Federico Chiesa", 26, 0, liverpoolId, 82, 76, 10, "RW"); // Liverpool
            footballPlayerService.save("Harvey Elliott", 21, 0, liverpoolId, 76, 83, 10, "CAM"); // Liverpool
            footballPlayerService.save("Curtis Jones", 23, 0, liverpoolId, 74, 82, 10, "CM"); // Liverpool
            footballPlayerService.save("Kostas Tsimikas", 28, 0, liverpoolId, 58, 80, 10, "LB"); // Liverpool
            footballPlayerService.save("Caoimhin Kelleher", 25, 0, liverpoolId, 15, 72, 81, "GK"); // Liverpool

        }

        int chelseaId = teamService.getOrSave("Chelsea", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(chelseaId)) {

            footballPlayerService.save("Robert Sanchez", 26, 0, chelseaId, 15, 70, 81, "GK"); // Chelsea
            footballPlayerService.save("Levi Colwill", 21, 0, chelseaId, 35, 78, 10, "CB"); // Chelsea
            footballPlayerService.save("Wesley Fofana", 23, 0, chelseaId, 30, 68, 10, "CB"); // Chelsea
            footballPlayerService.save("Reece James", 24, 0, chelseaId, 75, 84, 10, "RB"); // Chelsea
            footballPlayerService.save("Marc Cucurella", 25, 0, chelseaId, 55, 79, 10, "LB"); // Chelsea
            footballPlayerService.save("Moises Caicedo", 22, 0, chelseaId, 65, 85, 10, "CDM"); // Chelsea
            footballPlayerService.save("Enzo Fernandez", 23, 0, chelseaId, 78, 89, 10, "CM"); // Chelsea
            footballPlayerService.save("Cole Palmer", 22, 0, chelseaId, 86, 88, 10, "CAM"); // Chelsea
            footballPlayerService.save("Noni Madueke", 22, 0, chelseaId, 80, 76, 10, "RW"); // Chelsea
            footballPlayerService.save("Jadon Sancho", 24, 0, chelseaId, 77, 83, 10, "LW"); // Chelsea
            footballPlayerService.save("Nicolas Jackson", 23, 0, chelseaId, 81, 72, 10, "ST"); // Chelsea
            footballPlayerService.save("Christopher Nkunku", 26, 0, chelseaId, 85, 82, 10, "ST"); // Chelsea
            footballPlayerService.save("Pedro Neto", 24, 0, chelseaId, 79, 81, 10, "RW"); // Chelsea
            footballPlayerService.save("Joao Felix", 24, 0, chelseaId, 82, 81, 10, "CAM"); // Chelsea
            footballPlayerService.save("Romeo Lavia", 20, 0, chelseaId, 60, 82, 10, "CDM"); // Chelsea
            footballPlayerService.save("Malo Gusto", 21, 0, chelseaId, 50, 81, 10, "RB"); // Chelsea
            footballPlayerService.save("Axel Disasi", 26, 0, chelseaId, 45, 70, 10, "CB"); // Chelsea
            footballPlayerService.save("Filip Jorgensen", 22, 0, chelseaId, 15, 68, 79, "GK"); // Chelsea

        }

        int manUtdId = teamService.getOrSave("Manchester United", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(manUtdId)) {

            footballPlayerService.save("Andre Onana", 28, 0, manUtdId, 20, 85, 83, "GK"); // Man Utd
            footballPlayerService.save("Matthijs de Ligt", 24, 0, manUtdId, 45, 76, 10, "CB"); // Man Utd
            footballPlayerService.save("Lisandro Martinez", 26, 0, manUtdId, 40, 83, 10, "CB"); // Man Utd
            footballPlayerService.save("Diogo Dalot", 25, 0, manUtdId, 68, 81, 10, "RB"); // Man Utd
            footballPlayerService.save("Noussair Mazraoui", 26, 0, manUtdId, 60, 82, 10, "LB"); // Man Utd
            footballPlayerService.save("Manuel Ugarte", 23, 0, manUtdId, 55, 80, 10, "CDM"); // Man Utd
            footballPlayerService.save("Kobbie Mainoo", 19, 0, manUtdId, 72, 84, 10, "CM"); // Man Utd
            footballPlayerService.save("Bruno Fernandes", 29, 0, manUtdId, 85, 90, 10, "CAM"); // Man Utd
            footballPlayerService.save("Marcus Rashford", 26, 0, manUtdId, 83, 78, 10, "LW"); // Man Utd
            footballPlayerService.save("Alejandro Garnacho", 19, 0, manUtdId, 80, 76, 10, "RW"); // Man Utd
            footballPlayerService.save("Rasmus Højlund", 21, 0, manUtdId, 82, 68, 10, "ST"); // Man Utd
            footballPlayerService.save("Joshua Zirkzee", 23, 0, manUtdId, 79, 80, 10, "ST"); // Man Utd
            footballPlayerService.save("Amad Diallo", 21, 0, manUtdId, 76, 78, 10, "RW"); // Man Utd
            footballPlayerService.save("Mason Mount", 25, 0, manUtdId, 78, 83, 10, "CAM"); // Man Utd
            footballPlayerService.save("Casemiro", 32, 0, manUtdId, 73, 81, 10, "CDM"); // Man Utd
            footballPlayerService.save("Harry Maguire", 31, 0, manUtdId, 50, 75, 10, "CB"); // Man Utd
            footballPlayerService.save("Leny Yoro", 18, 0, manUtdId, 30, 75, 10, "CB"); // Man Utd
            footballPlayerService.save("Altay Bayindir", 26, 0, manUtdId, 10, 65, 78, "GK"); // Man Utd
        }

        int tottenhamId = teamService.getOrSave("Tottenham Hotspur", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(tottenhamId)) {
            footballPlayerService.save("Guglielmo Vicario", 27, 0, tottenhamId, 15, 70, 85, "GK"); // Tottenham
            footballPlayerService.save("Cristian Romero", 26, 0, tottenhamId, 45, 76, 10, "CB"); // Tottenham
            footballPlayerService.save("Micky van de Ven", 23, 0, tottenhamId, 40, 78, 10, "CB"); // Tottenham
            footballPlayerService.save("Pedro Porro", 24, 0, tottenhamId, 78, 84, 10, "RB"); // Tottenham
            footballPlayerService.save("Destiny Udogie", 21, 0, tottenhamId, 62, 80, 10, "LB"); // Tottenham
            footballPlayerService.save("Yves Bissouma", 27, 0, tottenhamId, 65, 83, 10, "CDM"); // Tottenham
            footballPlayerService.save("James Maddison", 27, 0, tottenhamId, 84, 89, 10, "CAM"); // Tottenham
            footballPlayerService.save("Pape Matar Sarr", 21, 0, tottenhamId, 72, 81, 10, "CM"); // Tottenham
            footballPlayerService.save("Son Heung-min", 31, 0, tottenhamId, 90, 83, 10, "LW"); // Tottenham
            footballPlayerService.save("Dejan Kulusevski", 24, 0, tottenhamId, 80, 85, 10, "RW"); // Tottenham
            footballPlayerService.save("Dominic Solanke", 26, 0, tottenhamId, 83, 72, 10, "ST"); // Tottenham
            footballPlayerService.save("Brennan Johnson", 23, 0, tottenhamId, 79, 75, 10, "RW"); // Tottenham
            footballPlayerService.save("Richarlison", 27, 0, tottenhamId, 81, 70, 10, "ST"); // Tottenham
            footballPlayerService.save("Rodrigo Bentancur", 27, 0, tottenhamId, 70, 85, 10, "CM"); // Tottenham
            footballPlayerService.save("Lucas Bergvall", 18, 0, tottenhamId, 68, 80, 10, "CM"); // Tottenham
            footballPlayerService.save("Radu Dragusin", 22, 0, tottenhamId, 35, 70, 10, "CB"); // Tottenham
            footballPlayerService.save("Timo Werner", 28, 0, tottenhamId, 77, 72, 10, "LW"); // Tottenham
            footballPlayerService.save("Fraser Forster", 36, 0, tottenhamId, 10, 60, 77, "GK"); // Tottenham

        }


        int astonVillaId = teamService.getOrSave("Aston Villa", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(astonVillaId)) {

            footballPlayerService.save("Emiliano Martinez", 31, 0, astonVillaId, 20, 75, 89, "GK"); // Aston Villa
            footballPlayerService.save("Ezri Konsa", 26, 0, astonVillaId, 35, 76, 10, "CB"); // Aston Villa
            footballPlayerService.save("Pau Torres", 27, 0, astonVillaId, 45, 85, 10, "CB"); // Aston Villa
            footballPlayerService.save("Matty Cash", 26, 0, astonVillaId, 68, 78, 10, "RB"); // Aston Villa
            footballPlayerService.save("Ian Maatsen", 22, 0, astonVillaId, 65, 80, 10, "LB"); // Aston Villa
            footballPlayerService.save("Amadou Onana", 22, 0, astonVillaId, 68, 81, 10, "CDM"); // Aston Villa
            footballPlayerService.save("Youri Tielemans", 27, 0, astonVillaId, 82, 88, 10, "CM"); // Aston Villa
            footballPlayerService.save("John McGinn", 29, 0, astonVillaId, 78, 84, 10, "CM"); // Aston Villa
            footballPlayerService.save("Leon Bailey", 26, 0, astonVillaId, 83, 80, 10, "RW"); // Aston Villa
            footballPlayerService.save("Morgan Rogers", 21, 0, astonVillaId, 79, 78, 10, "CAM"); // Aston Villa
            footballPlayerService.save("Ollie Watkins", 28, 0, astonVillaId, 88, 76, 10, "ST"); // Aston Villa
            footballPlayerService.save("Jhon Duran", 20, 0, astonVillaId, 84, 65, 10, "ST"); // Aston Villa
            footballPlayerService.save("Jacob Ramsey", 23, 0, astonVillaId, 77, 81, 10, "CM"); // Aston Villa
            footballPlayerService.save("Lucas Digne", 30, 0, astonVillaId, 65, 83, 10, "LB"); // Aston Villa
            footballPlayerService.save("Diego Carlos", 31, 0, astonVillaId, 40, 72, 10, "CB"); // Aston Villa
            footballPlayerService.save("Ross Barkley", 30, 0, astonVillaId, 78, 84, 10, "CM"); // Aston Villa
            footballPlayerService.save("Emiliano Buendia", 27, 0, astonVillaId, 79, 84, 10, "CAM"); // Aston Villa
            footballPlayerService.save("Robin Olsen", 34, 0, astonVillaId, 10, 62, 76, "GK"); // Aston Villa

        }


        int newcastleId = teamService.getOrSave("Newcastle United", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(newcastleId)) {

            footballPlayerService.save("Nick Pope", 32, 0, newcastleId, 15, 60, 85, "GK"); // Newcastle
            footballPlayerService.save("Sven Botman", 24, 0, newcastleId, 35, 78, 10, "CB"); // Newcastle
            footballPlayerService.save("Fabian Schär", 32, 0, newcastleId, 70, 81, 10, "CB"); // Newcastle
            footballPlayerService.save("Kieran Trippier", 33, 0, newcastleId, 72, 89, 10, "RB"); // Newcastle
            footballPlayerService.save("Lewis Hall", 19, 0, newcastleId, 65, 80, 10, "LB"); // Newcastle
            footballPlayerService.save("Bruno Guimarães", 26, 0, newcastleId, 78, 88, 10, "CM"); // Newcastle
            footballPlayerService.save("Joelinton", 27, 0, newcastleId, 75, 80, 10, "CM"); // Newcastle
            footballPlayerService.save("Sandro Tonali", 24, 0, newcastleId, 74, 85, 10, "CDM"); // Newcastle
            footballPlayerService.save("Anthony Gordon", 23, 0, newcastleId, 82, 81, 10, "LW"); // Newcastle
            footballPlayerService.save("Harvey Barnes", 26, 0, newcastleId, 84, 78, 10, "LW"); // Newcastle
            footballPlayerService.save("Alexander Isak", 24, 0, newcastleId, 90, 78, 10, "ST"); // Newcastle
            footballPlayerService.save("Callum Wilson", 32, 0, newcastleId, 85, 68, 10, "ST"); // Newcastle
            footballPlayerService.save("Jacob Murphy", 29, 0, newcastleId, 77, 79, 10, "RW"); // Newcastle
            footballPlayerService.save("Joe Willock", 24, 0, newcastleId, 76, 79, 10, "CM"); // Newcastle
            footballPlayerService.save("Dan Burn", 32, 0, newcastleId, 40, 72, 10, "CB"); // Newcastle
            footballPlayerService.save("Tino Livramento", 21, 0, newcastleId, 55, 78, 10, "RB"); // Newcastle
            footballPlayerService.save("Lloyd Kelly", 25, 0, newcastleId, 30, 74, 10, "CB"); // Newcastle
            footballPlayerService.save("Martin Dubravka", 35, 0, newcastleId, 10, 68, 79, "GK"); // Newcastle

        }


        int brightonId = teamService.getOrSave("Brighton", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(brightonId)) {

            footballPlayerService.save("Bart Verbruggen", 21, 0, brightonId, 15, 80, 81, "GK"); // Brighton
            footballPlayerService.save("Lewis Dunk", 32, 0, brightonId, 50, 80, 10, "CB"); // Brighton
            footballPlayerService.save("Jan Paul van Hecke", 24, 0, brightonId, 30, 79, 10, "CB"); // Brighton
            footballPlayerService.save("Joel Veltman", 32, 0, brightonId, 45, 75, 10, "RB"); // Brighton
            footballPlayerService.save("Pervis Estupiñán", 26, 0, brightonId, 65, 81, 10, "LB"); // Brighton
            footballPlayerService.save("Carlos Baleba", 20, 0, brightonId, 60, 82, 10, "CDM"); // Brighton
            footballPlayerService.save("Mats Wieffer", 24, 0, brightonId, 68, 83, 10, "CDM"); // Brighton
            footballPlayerService.save("Joao Pedro", 22, 0, brightonId, 84, 80, 10, "CAM"); // Brighton
            footballPlayerService.save("Kaoru Mitoma", 27, 0, brightonId, 80, 82, 10, "LW"); // Brighton
            footballPlayerService.save("Yankuba Minteh", 19, 0, brightonId, 78, 74, 10, "RW"); // Brighton
            footballPlayerService.save("Danny Welbeck", 33, 0, brightonId, 81, 75, 10, "ST"); // Brighton
            footballPlayerService.save("Evan Ferguson", 19, 0, brightonId, 83, 68, 10, "ST"); // Brighton
            footballPlayerService.save("Simon Adingra", 22, 0, brightonId, 79, 76, 10, "RW"); // Brighton
            footballPlayerService.save("Georginio Rutter", 22, 0, brightonId, 77, 81, 10, "CAM"); // Brighton
            footballPlayerService.save("Matt O'Riley", 23, 0, brightonId, 80, 85, 10, "CM"); // Brighton
            footballPlayerService.save("Ferdi Kadıoğlu", 24, 0, brightonId, 72, 84, 10, "LB"); // Brighton
            footballPlayerService.save("Igor Julio", 26, 0, brightonId, 35, 77, 10, "CB"); // Brighton
            footballPlayerService.save("Jason Steele", 33, 0, brightonId, 15, 84, 77, "GK"); // Brighton

        }

        int westHamId = teamService.getOrSave("West Ham United", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(westHamId)) {

            footballPlayerService.save("Alphonse Areola", 31, 0, westHamId, 15, 68, 84, "GK"); // West Ham
            footballPlayerService.save("Max Kilman", 27, 0, westHamId, 35, 79, 10, "CB"); // West Ham
            footballPlayerService.save("Jean-Clair Todibo", 24, 0, westHamId, 30, 77, 10, "CB"); // West Ham
            footballPlayerService.save("Aaron Wan-Bissaka", 26, 0, westHamId, 45, 74, 10, "RB"); // West Ham
            footballPlayerService.save("Emerson Palmieri", 29, 0, westHamId, 64, 80, 10, "LB"); // West Ham
            footballPlayerService.save("Guido Rodriguez", 30, 0, westHamId, 60, 83, 10, "CDM"); // West Ham
            footballPlayerService.save("Edson Alvarez", 26, 0, westHamId, 65, 81, 10, "CDM"); // West Ham
            footballPlayerService.save("Lucas Paqueta", 26, 0, westHamId, 82, 89, 10, "CAM"); // West Ham
            footballPlayerService.save("Mohammed Kudus", 23, 0, westHamId, 84, 80, 10, "RW"); // West Ham
            footballPlayerService.save("Jarrod Bowen", 27, 0, westHamId, 86, 82, 10, "RW"); // West Ham
            footballPlayerService.save("Niclas Füllkrug", 31, 0, westHamId, 85, 72, 10, "ST"); // West Ham
            footballPlayerService.save("Crysencio Summerville", 22, 0, westHamId, 80, 78, 10, "LW"); // West Ham
            footballPlayerService.save("Tomas Soucek", 29, 0, westHamId, 78, 75, 10, "CM"); // West Ham
            footballPlayerService.save("Carlos Soler", 27, 0, westHamId, 77, 85, 10, "CM"); // West Ham
            footballPlayerService.save("Konstantinos Mavropanos", 26, 0, westHamId, 40, 70, 10, "CB"); // West Ham
            footballPlayerService.save("Vladimir Coufal", 31, 0, westHamId, 58, 77, 10, "RB"); // West Ham
            footballPlayerService.save("Michail Antonio", 34, 0, westHamId, 80, 68, 10, "ST"); // West Ham
            footballPlayerService.save("Lukasz Fabianski", 39, 0, westHamId, 10, 65, 78, "GK"); // West Ham

        }

        int fulhamId = teamService.getOrSave("Fulham", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(fulhamId)) {

            footballPlayerService.save("Bernd Leno", 32, 0, fulhamId, 15, 72, 84, "GK"); // Fulham
            footballPlayerService.save("Joachim Andersen", 28, 0, fulhamId, 55, 84, 10, "CB"); // Fulham
            footballPlayerService.save("Calvin Bassey", 24, 0, fulhamId, 35, 75, 10, "CB"); // Fulham
            footballPlayerService.save("Kenny Tete", 28, 0, fulhamId, 58, 77, 10, "RB"); // Fulham
            footballPlayerService.save("Antonee Robinson", 26, 0, fulhamId, 62, 80, 10, "LB"); // Fulham
            footballPlayerService.save("Sander Berge", 26, 0, fulhamId, 68, 83, 10, "CDM"); // Fulham
            footballPlayerService.save("Andreas Pereira", 28, 0, fulhamId, 80, 86, 10, "CAM"); // Fulham
            footballPlayerService.save("Emile Smith Rowe", 24, 0, fulhamId, 79, 84, 10, "CAM"); // Fulham
            footballPlayerService.save("Alex Iwobi", 28, 0, fulhamId, 76, 82, 10, "RW"); // Fulham
            footballPlayerService.save("Adama Traore", 28, 0, fulhamId, 72, 74, 10, "RW"); // Fulham
            footballPlayerService.save("Raul Jimenez", 33, 0, fulhamId, 82, 75, 10, "ST"); // Fulham
            footballPlayerService.save("Rodrigo Muniz", 23, 0, fulhamId, 81, 65, 10, "ST"); // Fulham
            footballPlayerService.save("Harry Wilson", 27, 0, fulhamId, 82, 81, 10, "RW"); // Fulham
            footballPlayerService.save("Harrison Reed", 29, 0, fulhamId, 65, 80, 10, "CM"); // Fulham
            footballPlayerService.save("Timothy Castagne", 28, 0, fulhamId, 60, 78, 10, "RB"); // Fulham
            footballPlayerService.save("Sasa Lukic", 27, 0, fulhamId, 70, 82, 10, "CM"); // Fulham
            footballPlayerService.save("Issa Diop", 27, 0, fulhamId, 30, 70, 10, "CB"); // Fulham
            footballPlayerService.save("Steven Benda", 25, 0, fulhamId, 10, 60, 74, "GK"); // Fulham
        }

        int brentfordId = teamService.getOrSave("Brentford", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(brentfordId)) {

            footballPlayerService.save("Mark Flekken", 31, 0, brentfordId, 15, 75, 82, "GK"); // Brentford
            footballPlayerService.save("Ethan Pinnock", 31, 0, brentfordId, 40, 72, 10, "CB"); // Brentford
            footballPlayerService.save("Nathan Collins", 23, 0, brentfordId, 35, 74, 10, "CB"); // Brentford
            footballPlayerService.save("Kristoffer Ajer", 26, 0, brentfordId, 55, 78, 10, "RB"); // Brentford
            footballPlayerService.save("Rico Henry", 27, 0, brentfordId, 60, 77, 10, "LB"); // Brentford
            footballPlayerService.save("Christian Nørgaard", 30, 0, brentfordId, 65, 82, 10, "CDM"); // Brentford
            footballPlayerService.save("Vitaly Janelt", 26, 0, brentfordId, 70, 80, 10, "CM"); // Brentford
            footballPlayerService.save("Mathias Jensen", 28, 0, brentfordId, 74, 85, 10, "CM"); // Brentford
            footballPlayerService.save("Bryan Mbeumo", 24, 0, brentfordId, 85, 81, 10, "RW"); // Brentford
            footballPlayerService.save("Yoane Wissa", 27, 0, brentfordId, 83, 76, 10, "LW"); // Brentford
            footballPlayerService.save("Kevin Schade", 22, 0, brentfordId, 78, 72, 10, "ST"); // Brentford
            footballPlayerService.save("Mikkel Damsgaard", 24, 0, brentfordId, 72, 83, 10, "CAM"); // Brentford
            footballPlayerService.save("Fabio Carvalho", 21, 0, brentfordId, 77, 81, 10, "CAM"); // Brentford
            footballPlayerService.save("Sepp van den Berg", 22, 0, brentfordId, 30, 75, 10, "CB"); // Brentford
            footballPlayerService.save("Gustavo Nunes", 18, 0, brentfordId, 75, 74, 10, "LW"); // Brentford
            footballPlayerService.save("Keane Lewis-Potter", 23, 0, brentfordId, 76, 75, 10, "LW"); // Brentford
            footballPlayerService.save("Kristoffer Ajer", 26, 0, brentfordId, 55, 78, 10, "CB"); // Brentford
            footballPlayerService.save("Hakon Valdimarsson", 22, 0, brentfordId, 10, 60, 75, "GK"); // Brentford

        }

        int crystalPalaceId = teamService.getOrSave("Crystal Palace", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(crystalPalaceId)) {

            footballPlayerService.save("Dean Henderson", 27, 0, crystalPalaceId, 15, 70, 82, "GK"); // Palace
            footballPlayerService.save("Marc Guehi", 24, 0, crystalPalaceId, 40, 79, 10, "CB"); // Palace
            footballPlayerService.save("Maxence Lacroix", 24, 0, crystalPalaceId, 35, 72, 10, "CB"); // Palace
            footballPlayerService.save("Daniel Muñoz", 28, 0, crystalPalaceId, 68, 77, 10, "RB"); // Palace
            footballPlayerService.save("Tyrick Mitchell", 24, 0, crystalPalaceId, 55, 76, 10, "LB"); // Palace
            footballPlayerService.save("Adam Wharton", 20, 0, crystalPalaceId, 70, 86, 10, "CM"); // Palace
            footballPlayerService.save("Cheick Doucoure", 24, 0, crystalPalaceId, 65, 81, 10, "CDM"); // Palace
            footballPlayerService.save("Eberechi Eze", 26, 0, crystalPalaceId, 85, 88, 10, "CAM"); // Palace
            footballPlayerService.save("Daichi Kamada", 27, 0, crystalPalaceId, 78, 83, 10, "CAM"); // Palace
            footballPlayerService.save("Ismaila Sarr", 26, 0, crystalPalaceId, 80, 76, 10, "RW"); // Palace
            footballPlayerService.save("Jean-Philippe Mateta", 27, 0, crystalPalaceId, 86, 68, 10, "ST"); // Palace
            footballPlayerService.save("Eddie Nketiah", 25, 0, crystalPalaceId, 82, 70, 10, "ST"); // Palace
            footballPlayerService.save("Jefferson Lerma", 29, 0, crystalPalaceId, 72, 78, 10, "CDM"); // Palace
            footballPlayerService.save("Will Hughes", 29, 0, crystalPalaceId, 68, 81, 10, "CM"); // Palace
            footballPlayerService.save("Trevor Chalobah", 25, 0, crystalPalaceId, 45, 76, 10, "CB"); // Palace
            footballPlayerService.save("Chris Richards", 24, 0, crystalPalaceId, 35, 74, 10, "CB"); // Palace
            footballPlayerService.save("Matheus Franca", 20, 0, crystalPalaceId, 74, 78, 10, "CAM"); // Palace
            footballPlayerService.save("Matt Turner", 30, 0, crystalPalaceId, 10, 65, 77, "GK"); // Palace
        }

        int bournemouthId = teamService.getOrSave("Bournemouth", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(bournemouthId)) {

            footballPlayerService.save("Kepa Arrizabalaga", 29, 0, bournemouthId, 15, 78, 81, "GK"); // Bournemouth
            footballPlayerService.save("Illia Zabarnyi", 21, 0, bournemouthId, 30, 75, 10, "CB"); // Bournemouth
            footballPlayerService.save("Marcos Senesi", 27, 0, bournemouthId, 55, 80, 10, "CB"); // Bournemouth
            footballPlayerService.save("Julian Araujo", 22, 0, bournemouthId, 60, 76, 10, "RB"); // Bournemouth
            footballPlayerService.save("Milos Kerkez", 20, 0, bournemouthId, 62, 78, 10, "LB"); // Bournemouth
            footballPlayerService.save("Lewis Cook", 27, 0, bournemouthId, 70, 84, 10, "CM"); // Bournemouth
            footballPlayerService.save("Ryan Christie", 29, 0, bournemouthId, 75, 82, 10, "CM"); // Bournemouth
            footballPlayerService.save("Justin Kluivert", 25, 0, bournemouthId, 80, 78, 10, "CAM"); // Bournemouth
            footballPlayerService.save("Antoine Semenyo", 24, 0, bournemouthId, 82, 75, 10, "RW"); // Bournemouth
            footballPlayerService.save("Luis Sinisterra", 25, 0, bournemouthId, 81, 77, 10, "LW"); // Bournemouth
            footballPlayerService.save("Evanilson", 24, 0, bournemouthId, 84, 72, 10, "ST"); // Bournemouth
            footballPlayerService.save("Enes Ünal", 27, 0, bournemouthId, 80, 74, 10, "ST"); // Bournemouth
            footballPlayerService.save("Marcus Tavernier", 25, 0, bournemouthId, 78, 81, 10, "LW"); // Bournemouth
            footballPlayerService.save("Tyler Adams", 25, 0, bournemouthId, 60, 80, 10, "CDM"); // Bournemouth
            footballPlayerService.save("Alex Scott", 20, 0, bournemouthId, 72, 82, 10, "CM"); // Bournemouth
            footballPlayerService.save("Dean Huijsen", 19, 0, bournemouthId, 45, 78, 10, "CB"); // Bournemouth
            footballPlayerService.save("Dango Ouattara", 22, 0, bournemouthId, 77, 75, 10, "RW"); // Bournemouth
            footballPlayerService.save("Mark Travers", 25, 0, bournemouthId, 10, 60, 77, "GK"); // Bournemouth
        }

        int evertonId = teamService.getOrSave("Everton", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(evertonId)) {

            footballPlayerService.save("Jordan Pickford", 30, 0, evertonId, 25, 84, 84, "GK"); // Everton
            footballPlayerService.save("James Tarkowski", 31, 0, evertonId, 45, 72, 10, "CB"); // Everton
            footballPlayerService.save("Jarrad Branthwaite", 22, 0, evertonId, 35, 76, 10, "CB"); // Everton
            footballPlayerService.save("Nathan Patterson", 22, 0, evertonId, 58, 75, 10, "RB"); // Everton
            footballPlayerService.save("Vitaliy Mykolenko", 25, 0, evertonId, 55, 76, 10, "LB"); // Everton
            footballPlayerService.save("Idrissa Gueye", 34, 0, evertonId, 65, 80, 10, "CDM"); // Everton
            footballPlayerService.save("Abdoulaye Doucoure", 31, 0, evertonId, 78, 77, 10, "CM"); // Everton
            footballPlayerService.save("Dwight McNeil", 24, 0, evertonId, 80, 83, 10, "CAM"); // Everton
            footballPlayerService.save("Iliman Ndiaye", 24, 0, evertonId, 79, 81, 10, "LW"); // Everton
            footballPlayerService.save("Jack Harrison", 27, 0, evertonId, 77, 79, 10, "RW"); // Everton
            footballPlayerService.save("Dominic Calvert-Lewin", 27, 0, evertonId, 82, 68, 10, "ST"); // Everton
            footballPlayerService.save("Beto", 26, 0, evertonId, 78, 62, 10, "ST"); // Everton
            footballPlayerService.save("Orel Mangala", 26, 0, evertonId, 65, 82, 10, "CDM"); // Everton
            footballPlayerService.save("James Garner", 23, 0, evertonId, 74, 82, 10, "CM"); // Everton
            footballPlayerService.save("Tim Iroegbunam", 21, 0, evertonId, 64, 78, 10, "CM"); // Everton
            footballPlayerService.save("Jake O'Brien", 23, 0, evertonId, 35, 70, 10, "CB"); // Everton
            footballPlayerService.save("Ashley Young", 39, 0, evertonId, 68, 80, 10, "RB"); // Everton
            footballPlayerService.save("João Virgínia", 24, 0, evertonId, 10, 62, 74, "GK"); // Everton
        }


        int wolvesId = teamService.getOrSave("Wolverhampton Wanderers", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(wolvesId)) {

            footballPlayerService.save("Jose Sa", 31, 0, wolvesId, 15, 68, 82, "GK"); // Wolves
            footballPlayerService.save("Yerson Mosquera", 23, 0, wolvesId, 30, 72, 10, "CB"); // Wolves
            footballPlayerService.save("Craig Dawson", 34, 0, wolvesId, 45, 70, 10, "CB"); // Wolves
            footballPlayerService.save("Nelson Semedo", 30, 0, wolvesId, 60, 78, 10, "RB"); // Wolves
            footballPlayerService.save("Rayan Ait-Nouri", 23, 0, wolvesId, 74, 81, 10, "LB"); // Wolves
            footballPlayerService.save("Joao Gomes", 23, 0, wolvesId, 68, 82, 10, "CM"); // Wolves
            footballPlayerService.save("Andre", 23, 0, wolvesId, 65, 84, 10, "CDM"); // Wolves
            footballPlayerService.save("Mario Lemina", 30, 0, wolvesId, 72, 80, 10, "CM"); // Wolves
            footballPlayerService.save("Matheus Cunha", 25, 0, wolvesId, 84, 83, 10, "CAM"); // Wolves
            footballPlayerService.save("Hwang Hee-chan", 28, 0, wolvesId, 83, 75, 10, "RW"); // Wolves
            footballPlayerService.save("Jorgen Strand Larsen", 24, 0, wolvesId, 82, 68, 10, "ST"); // Wolves
            footballPlayerService.save("Goncalo Guedes", 27, 0, wolvesId, 80, 77, 10, "ST"); // Wolves
            footballPlayerService.save("Jean-Ricner Bellegarde", 26, 0, wolvesId, 74, 79, 10, "CM"); // Wolves
            footballPlayerService.save("Tommy Doyle", 22, 0, wolvesId, 70, 83, 10, "CM"); // Wolves
            footballPlayerService.save("Toti Gomes", 25, 0, wolvesId, 35, 74, 10, "CB"); // Wolves
            footballPlayerService.save("Matt Doherty", 32, 0, wolvesId, 65, 76, 10, "RB"); // Wolves
            footballPlayerService.save("Rodrigo Gomes", 21, 0, wolvesId, 75, 78, 10, "RW"); // Wolves
            footballPlayerService.save("Sam Johnstone", 31, 0, wolvesId, 10, 65, 79, "GK"); // Wolves
        }


        int forestId = teamService.getOrSave("Nottingham Forest", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(forestId)) {

            footballPlayerService.save("Matz Sels", 32, 0, forestId, 15, 68, 80, "GK"); // Forest
            footballPlayerService.save("Murillo", 22, 0, forestId, 45, 80, 10, "CB"); // Forest
            footballPlayerService.save("Nikola Milenkovic", 26, 0, forestId, 35, 74, 10, "CB"); // Forest
            footballPlayerService.save("Ola Aina", 27, 0, forestId, 65, 77, 10, "RB"); // Forest
            footballPlayerService.save("Alex Moreno", 31, 0, forestId, 68, 79, 10, "LB"); // Forest
            footballPlayerService.save("Ibrahim Sangare", 26, 0, forestId, 62, 81, 10, "CDM"); // Forest
            footballPlayerService.save("Elliot Anderson", 21, 0, forestId, 74, 82, 10, "CM"); // Forest
            footballPlayerService.save("Morgan Gibbs-White", 24, 0, forestId, 81, 86, 10, "CAM"); // Forest
            footballPlayerService.save("Callum Hudson-Odoi", 23, 0, forestId, 80, 78, 10, "LW"); // Forest
            footballPlayerService.save("Anthony Elanga", 22, 0, forestId, 78, 76, 10, "RW"); // Forest
            footballPlayerService.save("Chris Wood", 32, 0, forestId, 84, 65, 10, "ST"); // Forest
            footballPlayerService.save("Taiwo Awoniyi", 26, 0, forestId, 82, 68, 10, "ST"); // Forest
            footballPlayerService.save("James Ward-Prowse", 29, 0, forestId, 82, 90, 10, "CM"); // Forest
            footballPlayerService.save("Nicolas Dominguez", 26, 0, forestId, 72, 82, 10, "CM"); // Forest
            footballPlayerService.save("Morato", 23, 0, forestId, 30, 72, 10, "CB"); // Forest
            footballPlayerService.save("Neco Williams", 23, 0, forestId, 64, 78, 10, "RB"); // Forest
            footballPlayerService.save("Ramon Sosa", 24, 0, forestId, 77, 75, 10, "LW"); // Forest
            footballPlayerService.save("Carlos Miguel", 25, 0, forestId, 10, 60, 78, "GK"); // Forest

        }

        int leicesterId = teamService.getOrSave("Leicester City", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(leicesterId)) {
            footballPlayerService.save("Mads Hermansen", 23, 0, leicesterId, 15, 82, 80, "GK"); // Leicester
            footballPlayerService.save("Wout Faes", 26, 0, leicesterId, 35, 75, 10, "CB"); // Leicester
            footballPlayerService.save("Caleb Okoli", 23, 0, leicesterId, 25, 68, 10, "CB"); // Leicester
            footballPlayerService.save("James Justin", 26, 0, leicesterId, 62, 77, 10, "RB"); // Leicester
            footballPlayerService.save("Victor Kristiansen", 21, 0, leicesterId, 58, 76, 10, "LB"); // Leicester
            footballPlayerService.save("Harry Winks", 28, 0, leicesterId, 65, 86, 10, "CDM"); // Leicester
            footballPlayerService.save("Wilfred Ndidi", 27, 0, leicesterId, 62, 78, 10, "CM"); // Leicester
            footballPlayerService.save("Facundo Buonanotte", 19, 0, leicesterId, 79, 82, 10, "CAM"); // Leicester
            footballPlayerService.save("Stephy Mavididi", 26, 0, leicesterId, 80, 75, 10, "LW"); // Leicester
            footballPlayerService.save("Abdul Fatawu", 20, 0, leicesterId, 78, 77, 10, "RW"); // Leicester
            footballPlayerService.save("Jamie Vardy", 37, 0, leicesterId, 85, 68, 10, "ST"); // Leicester
            footballPlayerService.save("Odsonne Edouard", 26, 0, leicesterId, 80, 72, 10, "ST"); // Leicester
            footballPlayerService.save("Jordan Ayew", 32, 0, leicesterId, 76, 78, 10, "RW"); // Leicester
            footballPlayerService.save("Oliver Skipp", 23, 0, leicesterId, 60, 81, 10, "CDM"); // Leicester
            footballPlayerService.save("Boubakary Soumare", 25, 0, leicesterId, 65, 80, 10, "CM"); // Leicester
            footballPlayerService.save("Jannik Vestergaard", 31, 0, leicesterId, 50, 79, 10, "CB"); // Leicester
            footballPlayerService.save("Ricardo Pereira", 30, 0, leicesterId, 68, 82, 10, "RB"); // Leicester
            footballPlayerService.save("Danny Ward", 31, 0, leicesterId, 10, 65, 76, "GK"); // Leicester
        }

        int ipswichId = teamService.getOrSave("Ipswich Town", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(ipswichId)) {
            footballPlayerService.save("Arijanet Muric", 25, 0, ipswichId, 15, 78, 79, "GK"); // Ipswich
            footballPlayerService.save("Jacob Greaves", 23, 0, ipswichId, 30, 72, 10, "CB"); // Ipswich
            footballPlayerService.save("Dara O'Shea", 25, 0, ipswichId, 35, 74, 10, "CB"); // Ipswich
            footballPlayerService.save("Ben Johnson", 24, 0, ipswichId, 58, 75, 10, "RB"); // Ipswich
            footballPlayerService.save("Leif Davis", 24, 0, ipswichId, 65, 84, 10, "LB"); // Ipswich
            footballPlayerService.save("Kalvin Phillips", 28, 0, ipswichId, 68, 82, 10, "CDM"); // Ipswich
            footballPlayerService.save("Sam Morsy", 32, 0, ipswichId, 70, 78, 10, "CM"); // Ipswich
            footballPlayerService.save("Omari Hutchinson", 20, 0, ipswichId, 78, 80, 10, "CAM"); // Ipswich
            footballPlayerService.save("Sammie Szmodics", 28, 0, ipswichId, 82, 78, 10, "LW"); // Ipswich
            footballPlayerService.save("Chiedozie Ogbene", 27, 0, ipswichId, 77, 75, 10, "RW"); // Ipswich
            footballPlayerService.save("Liam Delap", 21, 0, ipswichId, 81, 65, 10, "ST"); // Ipswich
            footballPlayerService.save("George Hirst", 25, 0, ipswichId, 78, 62, 10, "ST"); // Ipswich
            footballPlayerService.save("Jack Clarke", 23, 0, ipswichId, 79, 80, 10, "LW"); // Ipswich
            footballPlayerService.save("Jens Cajuste", 24, 0, ipswichId, 65, 81, 10, "CM"); // Ipswich
            footballPlayerService.save("Conor Chaplin", 27, 0, ipswichId, 79, 77, 10, "CAM"); // Ipswich
            footballPlayerService.save("Luke Woolfenden", 25, 0, ipswichId, 25, 70, 10, "CB"); // Ipswich
            footballPlayerService.save("Axel Tuanzebe", 26, 0, ipswichId, 30, 72, 10, "RB"); // Ipswich
            footballPlayerService.save("Christian Walton", 28, 0, ipswichId, 10, 60, 75, "GK"); // Ipswich
        }

        int southamptonId = teamService.getOrSave("Southampton", premierLeague, footballId);

        if(!footballPlayerRepo.hasPlayers(southamptonId)) {

            footballPlayerService.save("Aaron Ramsdale", 26, 0, southamptonId, 15, 78, 82, "GK"); // Southampton
            footballPlayerService.save("Jan Bednarek", 28, 0, southamptonId, 35, 72, 10, "CB"); // Southampton
            footballPlayerService.save("Taylor Harwood-Bellis", 22, 0, southamptonId, 30, 78, 10, "CB"); // Southampton
            footballPlayerService.save("Yukinari Sugawara", 24, 0, southamptonId, 70, 81, 10, "RB"); // Southampton
            footballPlayerService.save("Kyle Walker-Peters", 27, 0, southamptonId, 65, 80, 10, "LB"); // Southampton
            footballPlayerService.save("Flynn Downes", 25, 0, southamptonId, 62, 82, 10, "CDM"); // Southampton
            footballPlayerService.save("Mateus Fernandes", 19, 0, southamptonId, 74, 83, 10, "CM"); // Southampton
            footballPlayerService.save("Adam Lallana", 36, 0, southamptonId, 76, 85, 10, "CAM"); // Southampton
            footballPlayerService.save("Tyler Dibling", 18, 0, southamptonId, 78, 77, 10, "RW"); // Southampton
            footballPlayerService.save("Ben Brereton Diaz", 25, 0, southamptonId, 80, 72, 10, "LW"); // Southampton
            footballPlayerService.save("Cameron Archer", 22, 0, southamptonId, 81, 68, 10, "ST"); // Southampton
            footballPlayerService.save("Adam Armstrong", 27, 0, southamptonId, 79, 74, 10, "ST"); // Southampton
            footballPlayerService.save("Joe Aribo", 27, 0, southamptonId, 75, 79, 10, "CM"); // Southampton
            footballPlayerService.save("Ryan Manning", 28, 0, southamptonId, 62, 81, 10, "LB"); // Southampton
            footballPlayerService.save("Jack Stephens", 30, 0, southamptonId, 40, 75, 10, "CB"); // Southampton
            footballPlayerService.save("Will Smallbone", 24, 0, southamptonId, 72, 82, 10, "CM"); // Southampton
            footballPlayerService.save("Paul Onuachu", 30, 0, southamptonId, 82, 60, 10, "ST"); // Southampton
            footballPlayerService.save("Alex McCarthy", 34, 0, southamptonId, 10, 60, 77, "GK"); // Southampton
        }
    }

    public static void main(String[] args) {





    }
}
