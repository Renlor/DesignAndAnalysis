/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Project2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author justin
 */
public class DesignAndAnalysis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] nodes48 = {
            {6734, 1453}, {2233, 10}, {5530, 1424}, {401, 841}, {3082, 1644},
            {7608, 4458}, {7573, 3716}, {7265, 1268}, {6898, 1885}, {1112, 2049},
            {5468, 2606}, {5989, 2873}, {4706, 2674}, {4612, 2035}, {6347, 2683},
            {6107, 669}, {7611, 5184}, {7462, 3590}, {7732, 4723}, {5900, 3561},
            {4483, 3369}, {6101, 1110}, {5199, 2182}, {1633, 2809}, {4307, 2322},
            {675, 1006}, {7555, 4819}, {7541, 3981}, {3177, 756}, {7352, 4506},
            {7545, 2801}, {3245, 3305}, {6426, 3173}, {4608, 1198}, {23, 2216},
            {7248, 3779}, {7762, 4595}, {7392, 2244}, {3484, 2829}, {6271, 2135},
            {4985, 140}, {1916, 1569}, {7280, 4899}, {7509, 3239}, {10, 2676},
            {6807, 2993}, {5185, 3258}, {3023, 1942}};
        int[][] nodes42 = {{170, 85},
            {166, 88}, {133, 73}, {140, 70}, {142, 55}, {126, 53}, {125, 60},
            {119, 68}, {117, 74}, {99, 83}, {73, 79}, {72, 91}, {37, 94},
            {6, 106}, {3, 97}, {21, 82}, {33, 67}, {4, 66}, {3, 42},
            {27, 33}, {52, 41}, {57, 59}, {58, 66}, {88, 65}, {99, 67},
            {95, 55}, {89, 55}, {83, 38}, {85, 25}, {104, 35}, {112, 37},
            {112, 24}, {113, 13}, {125, 30}, {135, 32}, {147, 18}, {147, 36},
            {154, 45}, {157, 54}, {158, 61}, {172, 82}, {174, 87}};
        int[][] dist26 = {
            {0, 83, 93, 129, 133, 139, 151, 169, 135, 114, 110, 98, 99, 95, 81, 152, 159, 181, 172, 185, 147, 157, 185, 220, 127, 181},
            {83, 0, 40, 53, 62, 64, 91, 116, 93, 84, 95, 98, 89, 68, 67, 127, 156, 175, 152, 165, 160, 180, 223, 268, 179, 197},
            {93, 40, 0, 42, 42, 49, 59, 81, 54, 44, 58, 64, 54, 31, 36, 86, 117, 135, 112, 125, 124, 147, 193, 241, 157, 161},
            {129, 53, 42, 0, 11, 11, 46, 72, 65, 70, 88, 100, 89, 66, 76, 102, 142, 156, 127, 139, 155, 180, 228, 278, 197, 190},
            {133, 62, 42, 11, 0, 9, 35, 61, 55, 62, 82, 95, 84, 62, 74, 93, 133, 146, 117, 128, 148, 173, 222, 272, 194, 182},
            {139, 64, 49, 11, 9, 0, 39, 65, 63, 71, 90, 103, 92, 71, 82, 100, 141, 153, 124, 135, 156, 181, 230, 280, 202, 190},
            {151, 91, 59, 46, 35, 39, 0, 26, 34, 52, 71, 88, 77, 63, 78, 66, 110, 119, 88, 98, 130, 156, 206, 257, 188, 160},
            {169, 116, 81, 72, 61, 65, 26, 0, 37, 59, 75, 92, 83, 76, 91, 54, 98, 103, 70, 78, 122, 148, 198, 250, 188, 148},
            {135, 93, 54, 65, 55, 63, 34, 37, 0, 22, 39, 56, 47, 40, 55, 37, 78, 91, 62, 74, 96, 122, 172, 223, 155, 128},
            {114, 84, 44, 70, 62, 71, 52, 59, 22, 0, 20, 36, 26, 20, 34, 43, 74, 91, 68, 82, 86, 111, 160, 210, 136, 121},
            {110, 95, 58, 88, 82, 90, 71, 75, 39, 20, 0, 18, 11, 27, 32, 42, 61, 80, 64, 77, 68, 92, 140, 190, 116, 103},
            {98, 98, 64, 100, 95, 103, 88, 92, 56, 36, 18, 0, 11, 34, 31, 56, 63, 85, 75, 87, 62, 83, 129, 178, 100, 99},
            {99, 89, 54, 89, 84, 92, 77, 83, 47, 26, 11, 11, 0, 23, 24, 53, 68, 89, 74, 87, 71, 93, 140, 189, 111, 107},
            {95, 68, 31, 66, 62, 71, 63, 76, 40, 20, 27, 34, 23, 0, 15, 62, 87, 106, 87, 100, 93, 116, 163, 212, 132, 130},
            {81, 67, 36, 76, 74, 82, 78, 91, 55, 34, 32, 31, 24, 15, 0, 73, 92, 112, 96, 109, 93, 113, 158, 205, 122, 130},
            {152, 127, 86, 102, 93, 100, 66, 54, 37, 43, 42, 56, 53, 62, 73, 0, 44, 54, 26, 39, 68, 94, 144, 196, 139, 95},
            {159, 156, 117, 142, 133, 141, 110, 98, 78, 74, 61, 63, 68, 87, 92, 44, 0, 22, 34, 38, 30, 53, 102, 154, 109, 51},
            {181, 175, 135, 156, 146, 153, 119, 103, 91, 91, 80, 85, 89, 106, 112, 54, 22, 0, 33, 29, 46, 64, 107, 157, 125, 51},
            {172, 152, 112, 127, 117, 124, 88, 70, 62, 68, 64, 75, 74, 87, 96, 26, 34, 33, 0, 13, 63, 87, 135, 186, 141, 81},
            {185, 165, 125, 139, 128, 135, 98, 78, 74, 82, 77, 87, 87, 100, 109, 39, 38, 29, 13, 0, 68, 90, 136, 186, 148, 79},
            {147, 160, 124, 155, 148, 156, 130, 122, 96, 86, 68, 62, 71, 93, 93, 68, 30, 46, 63, 68, 0, 26, 77, 128, 80, 37},
            {157, 180, 147, 180, 173, 181, 156, 148, 122, 111, 92, 83, 93, 116, 113, 94, 53, 64, 87, 90, 26, 0, 50, 102, 65, 27},
            {185, 223, 193, 228, 222, 230, 206, 198, 172, 160, 140, 129, 140, 163, 158, 144, 102, 107, 135, 136, 77, 50, 0, 51, 64, 58},
            {220, 268, 241, 278, 272, 280, 257, 250, 223, 210, 190, 178, 189, 212, 205, 196, 154, 157, 186, 186, 128, 102, 51, 0, 93, 107},
            {127, 179, 157, 197, 194, 202, 188, 188, 155, 136, 116, 100, 111, 132, 122, 139, 109, 125, 141, 148, 80, 65, 64, 93, 0, 90},
            {181, 197, 161, 190, 182, 190, 160, 148, 128, 121, 103, 99, 107, 130, 130, 95, 51, 51, 81, 79, 37, 27, 58, 107, 90, 0}
        };

        int[][] gr17 = {
            {0, 633, 257, 91, 412, 150, 80, 134, 259, 505, 353, 324, 70, 211, 268, 246, 121},
            {633, 0, 390, 661, 227, 488, 572, 530, 555, 289, 282, 638, 567, 466, 420, 745, 518},
            {257, 390, 0, 228, 169, 112, 196, 154, 372, 262, 110, 437, 191, 74, 53, 472, 142},
            {91, 661, 228, 0, 383, 120, 77, 105, 175, 476, 324, 240, 27, 182, 239, 237, 84},
            {412, 227, 169, 383, 0, 267, 351, 309, 338, 196, 61, 421, 346, 243, 199, 528, 297},
            {150, 488, 112, 120, 267, 0, 63, 34, 264, 360, 208, 329, 83, 105, 123, 364, 35},
            {80, 572, 196, 77, 351, 63, 0, 29, 232, 444, 292, 297, 47, 150, 207, 332, 29},
            {134, 530, 154, 105, 309, 34, 29, 0, 249, 402, 250, 314, 68, 108, 165, 349, 36},
            {259, 555, 372, 175, 338, 264, 232, 249, 0, 495, 352, 95, 189, 326, 383, 202, 236},
            {505, 289, 262, 476, 196, 360, 444, 402, 495, 0, 154, 578, 439, 336, 240, 685, 390},
            {353, 282, 110, 324, 61, 208, 292, 250, 352, 154, 0, 435, 287, 184, 140, 542, 238},
            {324, 638, 437, 240, 421, 329, 297, 314, 95, 578, 435, 0, 254, 391, 448, 157, 301},
            {70, 567, 191, 27, 346, 83, 47, 68, 189, 439, 287, 254, 0, 145, 202, 289, 55},
            {211, 466, 74, 182, 243, 105, 150, 108, 326, 336, 184, 391, 145, 0, 57, 426, 96},
            {268, 420, 53, 239, 199, 123, 207, 165, 383, 240, 140, 448, 202, 57, 0, 483, 153},
            {246, 745, 472, 237, 528, 364, 332, 349, 202, 685, 542, 157, 289, 426, 483, 0, 336},
            {121, 518, 142, 84, 297, 35, 29, 36, 236, 390, 238, 301, 55, 96, 153, 336, 0}
        };
        int[][] tsp15 = {
            {0, 29, 82, 46, 68, 52, 72, 42, 51, 55, 29, 74, 23, 72, 46},
            {29, 0, 55, 46, 42, 43, 43, 23, 23, 31, 41, 51, 11, 52, 21},
            {82, 55, 0, 68, 46, 55, 23, 43, 41, 29, 79, 21, 64, 31, 51},
            {46, 46, 68, 0, 82, 15, 72, 31, 62, 42, 21, 51, 51, 43, 64},
            {68, 42, 46, 82, 0, 74, 23, 52, 21, 46, 82, 58, 46, 65, 23},
            {52, 43, 55, 15, 74, 0, 61, 23, 55, 31, 33, 37, 51, 29, 59},
            {72, 43, 23, 72, 23, 61, 0, 42, 23, 31, 77, 37, 51, 46, 33},
            {42, 23, 43, 31, 52, 23, 42, 0, 33, 15, 37, 33, 33, 31, 37},
            {51, 23, 41, 62, 21, 55, 23, 33, 0, 29, 62, 46, 29, 51, 11},
            {55, 31, 29, 42, 46, 31, 31, 15, 29, 0, 51, 21, 41, 23, 37},
            {29, 41, 79, 21, 82, 33, 77, 37, 62, 51, 0, 65, 42, 59, 61},
            {74, 51, 21, 51, 58, 37, 37, 33, 46, 21, 65, 0, 61, 11, 55},
            {23, 11, 64, 51, 46, 51, 51, 33, 29, 41, 42, 61, 0, 62, 23},
            {72, 52, 31, 43, 65, 29, 46, 31, 51, 23, 59, 11, 62, 0, 59},
            {46, 21, 51, 64, 23, 59, 33, 37, 11, 37, 61, 55, 23, 59, 0}
        };
        int[] knap01w = {23, 31, 29, 44, 53, 38, 63, 85, 89, 82};
        int[] knap01p = {92, 57, 49, 68, 60, 43, 67, 84, 87, 72};
        int knap01c = 165;
        int[] knap02w = {12, 7, 11, 8, 9};
        int[] knap02p = {24, 13, 23, 15, 16,};
        int knap02c = 26;
        int[] knap03w = {56, 59, 80, 64, 75, 17};
        int[] knap03p = {50, 50, 64, 46, 50, 5};
        int knap03c = 190;
        int[] knap04w = {31, 10, 20, 19, 4, 3, 6};
        int[] knap04p = {70, 20, 39, 37, 7, 5, 10};
        int knap04c = 50;
        int[] knap05w = {25, 35, 45, 5, 25, 3, 2, 2};
        int[] knap05p = {350, 400, 450, 20, 70, 8, 5, 5};
        int knap05c = 104;
        int[] knap06w = {41, 50, 49, 59, 55, 57, 60};
        int[] knap06p = {442, 525, 511, 593, 546, 564, 617};
        int knap06c = 170;
        int[] knap07w = {70, 73, 77, 80, 82, 87, 90, 94, 98, 106, 110, 113, 115, 118, 120};
        int[] knap07p = {135, 139, 149, 150, 156, 163, 173, 184, 192, 201, 210, 214, 221, 229, 240};
        int knap07c = 750;
        int[] knap08w = {382745, 799601, 909247, 729069, 467902, 44328, 34610, 698150, 823460,
            903959, 853665, 551830, 610856, 670702, 488960, 951111, 323046, 446298, 931161, 31385,
            496951, 264724, 224916, 169684};
        int[] knap08p = {825594, 1677009, 1676628, 1523970, 943972, 97426, 69666, 1296457,
            1679693, 1902996, 1844992, 1049289, 1252836, 1319836, 953277, 2067538, 675367, 853655,
            1826027, 65731, 901489, 577243, 466257, 369261};
        int knap08c = 6404180;

        System.out.println("TSP Problem 1:");
        ArrayList<Node> tspNodes = new ArrayList<>();
        for (int i = 0; i < nodes48.length; i++) {
            tspNodes.add(new Node(nodes48[i][0], nodes48[i][1]));
        }
        for (int i = 0; i < tspNodes.size(); i++) {
            tspNodes.get(i).populateConnections(tspNodes);
        }

        Map map = new Map(Map.tsp2d, "tspsp01", tspNodes);

        TSPAnt ant = new TSPAnt(map, 2, 1, 10.0, .5);
        ant.run();

        System.out.println("TSP Problem 2:");
        tspNodes = new ArrayList<>();
        for (int i = 0; i < nodes42.length; i++) {
            tspNodes.add(new Node(nodes42[i][0], nodes42[i][1]));
        }
        for (int i = 0; i < tspNodes.size(); i++) {
            tspNodes.get(i).populateConnections(tspNodes);
        }

        map = new Map(Map.tsp2d, "tspsp02", tspNodes);
        ant = new TSPAnt(map, 2, 1, 1.0, .5);
        ant.run();

        System.out.println("TSP Problem 3:");
        tspNodes = new ArrayList<>();
        for (int i = 0; i < dist26.length; i++) {
            tspNodes.add(new Node());
        }
        for (int j = 0; j < dist26.length; j++) {
            HashMap<Node, Double> conn = new HashMap<>();
            for (int i = 0; i < dist26.length; i++) {
                conn.put(tspNodes.get(i), (double) dist26[j][i]);
            }
            tspNodes.get(j).setConnections(conn);
        }

        map = new Map(Map.tsp2d, "tspsp03", tspNodes);
        ant = new TSPAnt(map, 2, 1, 1.0, .5);
        ant.run();

        System.out.println("TSP Problem 4:");
        tspNodes = new ArrayList<>();
        for (int i = 0; i < gr17.length; i++) {
            tspNodes.add(new Node());
        }
        for (int j = 0; j < gr17.length; j++) {
            HashMap<Node, Double> conn = new HashMap<>();
            for (int i = 0; i < gr17.length; i++) {
                conn.put(tspNodes.get(i), (double) gr17[j][i]);
            }
            tspNodes.get(j).setConnections(conn);
        }

        map = new Map(Map.tsp2d, "tspsp04", tspNodes);
        ant = new TSPAnt(map, 2, 1, 1.0, .5);
        ant.run();

        System.out.println("TSP Problem 5:");
        tspNodes = new ArrayList<>();
        for (int i = 0; i < tsp15.length; i++) {
            tspNodes.add(new Node());
        }
        for (int j = 0; j < tsp15.length; j++) {
            HashMap<Node, Double> conn = new HashMap<>();
            for (int i = 0; i < tsp15.length; i++) {
                conn.put(tspNodes.get(i), (double) tsp15[j][i]);
            }
            tspNodes.get(j).setConnections(conn);
        }

        map = new Map(Map.tsp2d, "tspsp05", tspNodes);
        ant = new TSPAnt(map, 2, 1, 1.0, .5);
        ant.run();
        
        System.out.println("KSP Problem 1:");
        
        ArrayList<Node> kspNodes = new ArrayList<>();
        for (int i = 0; i < knap01w.length; i++) {
            kspNodes.add(new Node(knap01w[i], knap01p[i]));
        }
        for (int i = 0; i < kspNodes.size(); i++) {
            kspNodes.get(i);
        }
        
        map = new Map(Map.ksp, "knssp01", kspNodes);

        KSPAnt kspAnt = new KSPAnt(map, 2, 1, 1.0, .4, knap01c);
        kspAnt.run();
        
        System.out.println("KSP Problem 2:");
        kspNodes = new ArrayList<>();
        for (int i = 0; i < knap02w.length; i++) {
            kspNodes.add(new Node(knap02w[i], knap02p[i]));
        }
        for (int i = 0; i < kspNodes.size(); i++) {
            kspNodes.get(i);
        }
        
        map = new Map(Map.ksp, "knssp02", kspNodes);
        kspAnt = new KSPAnt(map, 2, 1, 1.0, .4, knap02c);
        kspAnt.run();
        
        System.out.println("KSP Problem 3:");
        kspNodes = new ArrayList<>();
        for (int i = 0; i < knap03w.length; i++) {
            kspNodes.add(new Node(knap03w[i], knap03p[i]));
        }
        for (int i = 0; i < kspNodes.size(); i++) {
            kspNodes.get(i);
        }
        
        map = new Map(Map.ksp, "knssp03", kspNodes);
        kspAnt = new KSPAnt(map, 2, 1, 1.0, .4, knap03c);
        kspAnt.run();
        
        System.out.println("KSP Problem 4:");
        kspNodes = new ArrayList<>();
        for (int i = 0; i < knap04w.length; i++) {
            kspNodes.add(new Node(knap04w[i], knap04p[i]));
        }
        for (int i = 0; i < kspNodes.size(); i++) {
            kspNodes.get(i);
        }
        
        map = new Map(Map.ksp, "knssp04", kspNodes);
        kspAnt = new KSPAnt(map, 2, 1, 1.0, .4, knap04c);
        kspAnt.run();
        
        System.out.println("KSP Problem 5:");
       kspNodes = new ArrayList<>();
        for (int i = 0; i < knap05w.length; i++) {
            kspNodes.add(new Node(knap05w[i], knap05p[i]));
        }
        for (int i = 0; i < kspNodes.size(); i++) {
            kspNodes.get(i);
        }
        
        map = new Map(Map.ksp, "knssp05", kspNodes);
        kspAnt = new KSPAnt(map, 2, 1, 1.0, .4, knap05c);
        kspAnt.run();
        
        System.out.println("KSP Problem 6:");
        kspNodes = new ArrayList<>();
        for (int i = 0; i < knap06w.length; i++) {
            kspNodes.add(new Node(knap06w[i], knap06p[i]));
        }
        for (int i = 0; i < kspNodes.size(); i++) {
            kspNodes.get(i);
        }
        
        map = new Map(Map.ksp, "knssp06", kspNodes);
        kspAnt = new KSPAnt(map, 2, 1, 1.0, .4, knap06c);
        kspAnt.run();
        
        System.out.println("KSP Problem 7:");
        kspNodes = new ArrayList<>();
        for (int i = 0; i < knap07w.length; i++) {
            kspNodes.add(new Node(knap07w[i], knap07p[i]));
        }
        for (int i = 0; i < kspNodes.size(); i++) {
            kspNodes.get(i);
        }
        
        map = new Map(Map.ksp, "knssp07", kspNodes);
        kspAnt = new KSPAnt(map, 2, 1, 1.0, .4, knap07c);
        kspAnt.run();
        
        System.out.println("KSP Problem 8:");
        kspNodes = new ArrayList<>();
        for (int i = 0; i < knap08w.length; i++) {
            kspNodes.add(new Node(knap08w[i], knap08p[i]));
        }
        for (int i = 0; i < kspNodes.size(); i++) {
            kspNodes.get(i);
        }
        
        map = new Map(Map.ksp, "knssp08", kspNodes);
        kspAnt = new KSPAnt(map, 2, 1, 1.0, .4, knap08c);
        kspAnt.run();

        /*Thread tsp = new Thread(new TSPBrute(100, 100, 0, 120));
        tsp.start();
        Thread ksp = new Thread(new KSPBrute(120, 100, 10));
        nsp.start();
        Map map = Map.generateMap(Map.ksp, "nsp", 500, 10, 100, 0);
        Thread kspa = new Thread(new KSPAnt(map, 10, 1, 1.0, .4, 20));
        nspa.start();*/
    }
}
