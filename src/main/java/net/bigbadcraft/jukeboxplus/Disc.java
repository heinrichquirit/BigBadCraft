package main.java.net.bigbadcraft.jukeboxplus;

import org.bukkit.Material;

public enum Disc {
        
        THIRTEEN(1, "Thirteen"), CAT(2, "Cat"), BLOCKS(3, "Blocks"), CHIRP(4, "Chirp"), FAR(5, "Far"),
        MALL(6, "Mall"), MELLOHI(7, "Mellohi"), STAL(8, "Stal"), STRAD(9, "Strad"), WARD(10, "Ward"),
        ELEVEN(11, "Eleven"), WAIT(12, "Wait");
        
        private int id;
        private String name;
        
        private Disc(int id, String name) {
                this.id = id;
                this.name = name;
        }
        
        public static String getEnumName(Material material) {
                switch(material) {
                case GOLD_RECORD: return Disc.getName(Disc.THIRTEEN);
                case GREEN_RECORD: return Disc.getName(Disc.CAT);
                case RECORD_3: return Disc.getName(Disc.BLOCKS);
                case RECORD_4: return Disc.getName(Disc.CHIRP);
                case RECORD_5: return Disc.getName(Disc.FAR);
                case RECORD_6: return Disc.getName(Disc.MALL);
                case RECORD_7: return Disc.getName(Disc.MELLOHI);
                case RECORD_8: return Disc.getName(Disc.STAL);
                case RECORD_9: return Disc.getName(Disc.STRAD);
                case RECORD_10: return Disc.getName(Disc.WARD);
                case RECORD_11: return Disc.getName(Disc.ELEVEN);
                case RECORD_12: return Disc.getName(Disc.WAIT);
                default: return "";
                }
        }
        
        public static int getEnumId(Material material) {
                switch(material) {
                case GOLD_RECORD: return Disc.getId(Disc.THIRTEEN);
                case GREEN_RECORD: return Disc.getId(Disc.CAT);
                case RECORD_3: return Disc.getId(Disc.BLOCKS);
                case RECORD_4: return Disc.getId(Disc.CHIRP);
                case RECORD_5: return Disc.getId(Disc.FAR);
                case RECORD_6: return Disc.getId(Disc.MALL);
                case RECORD_7: return Disc.getId(Disc.MELLOHI);
                case RECORD_8: return Disc.getId(Disc.STAL);
                case RECORD_9: return Disc.getId(Disc.STRAD);
                case RECORD_10: return Disc.getId(Disc.WARD);
                case RECORD_11: return Disc.getId(Disc.ELEVEN);
                case RECORD_12: return Disc.getId(Disc.WAIT);
                default: return 0;
                }
        }
        
        public static Material getRecord(int discEnumId) {
                switch(discEnumId) {
                case 1: return Material.GOLD_RECORD;
                case 2: return Material.GREEN_RECORD;
                case 3: return Material.RECORD_3;
                case 4: return Material.RECORD_4;
                case 5: return Material.RECORD_5;
                case 6: return Material.RECORD_6;
                case 7: return Material.RECORD_7;
                case 8: return Material.RECORD_8;
                case 9: return Material.RECORD_9;
                case 10: return Material.RECORD_10;
                case 11: return Material.RECORD_11;
                case 12: return Material.RECORD_12;
                default: return null;
                }
        }
        
        public static int getId(Disc disc) {
                return disc.getValue();
        }
        
        private int getValue() {
                return id;
        }
        
        public static String getName(Disc disc) {
                return disc.getNameValue();
        }
        
        private String getNameValue() {
                return name;
        }
}