package ca.sfu.cmpt213.Assignment2;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

public class Tokimons {
    private  String extra_comments;
    private  List<Team> team;

    @Override
    public String toString() {
        return "Tokimons{" +
                "extra_comments='" + extra_comments + '\'' +
                ", team=" + team +
                '}';
    }

    public Tokimons(String extra_comments, List<Team> team) {
        this.extra_comments = extra_comments;
        this.team = team;
    }


    public String getExtra_comments() {
        return extra_comments;
    }

    public List<Team> getTeam() {
        return team;
    }

    static class Compatibility {
        private double score;
        private String comment;

        @Override
        public String toString() {
            return "Compatibility{" +
                    "comment='" + comment + '\'' +
                    ", score=" + score +
                    '}';
        }

        public Compatibility(double score,String comment) {
            this.comment = comment;
            this.score = score;
        }


        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }

    static class Team {
        private String name;
        private String id;
        private Compatibility compatibility;

        @Override
        public String toString() {
            return "Team{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", compatibility=" + compatibility +
                    '}';
        }

        public Team(String name, String id, Compatibility compatibility) {
            this.name = name;
            this.id = id;
            this.compatibility = compatibility;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Compatibility getCompatibility() {
            return compatibility;
        }

//        public void setCompatibility(Compatibility compatibility) {
//            this.compatibility = compatibility;
//        }
    }
}

