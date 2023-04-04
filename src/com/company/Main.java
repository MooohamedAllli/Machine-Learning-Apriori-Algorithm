package com.company;
import java.io.*;
import java.util.*;

public class Main {

    /*Data base Transaction*/
    static class Transactioin {
        int id;
        Vector<String> value = new Vector<>();
    }
    /********************************************/

    /*The historical data*/
    static class itemset {
        Vector <String> set = new Vector<>();
        float counter =0;
    }
    /********************************************/

    public static double count(Vector <String> v , ArrayList<Transactioin> t ) {
        v.remove(v.size()-1);
        int c =  0;
        int test =0;
        for (int i=0 ; i<t.size() ; i++){
            for(int j=0 ; j< v.size() ; j++) {
                 if (t.contains(v.get(j))){
                     test++;
                 }
                 if (test == v.size()) {
                    c++;
                    test =0;
                 }
                 else c=1;

            }
        }
        return c;
    }


    /*Get the uniqe items*/
    public static Vector <String> get_uniqe(ArrayList<Transactioin> e) {
        Vector<String> uniqe = new Vector<>();

        for (int i = 0; i < e.size(); i++) {
            for (int j = 0; j < e.get(i).value.size(); j++) {
                if (uniqe.contains(e.get(i).value.get(j)))
                    continue;
                else
                    uniqe.add(e.get(i).value.get(j));
            }
        }
        return uniqe;
    }
    /********************************************/

    public static Vector <String> get_uniqe_items(ArrayList <itemset> e) {
        Vector<String> uniqe = new Vector<>();

        for (int i = 0; i < e.size(); i++) {
            for (int j = 0; j < e.get(i).set.size(); j++) {
                if (uniqe.contains(e.get(i).set.get(j)))
                    continue;
                else
                    uniqe.add(e.get(i).set.get(j));
            }
        }
        return uniqe;
    }
    /********************************************/

    public static ArrayList <itemset> itemSet_builder(Vector <String> v , ArrayList<Transactioin> t){
        ArrayList <itemset> sets = new ArrayList<>();
        itemset item = new itemset();

        for (int i=0; i<v.size() ; i++) {
            item.set.add(v.get(i));
            sets.add(item);
            item = new itemset();
        }
        for (int i=0 ; i<t.size() ; i++) {
            for (int j= 0; j<sets.size() ; j++){
                for (int h =0 ; h < sets.get(j).set.size() ; h++){
                    if(t.get(i).value.contains(sets.get(j).set.get(h))){
                        sets.get(j).counter = sets.get(j).counter +1;
                    }
                }
            }
        }
        ///TRY
    /*    for (int i=0; i<v.size() ; i++) {
            for (int j=0 ; j<sets.get(i).set.size(); j++)
                System.out.println(sets.get(i).set.get(j) +" "+ sets.get(i).counter);
        }
        */
        return sets;
    }
    /********************************************/

    public static Vector<String> chech_minSup( ArrayList <itemset> item , int minSup){

        Vector <String> s = new Vector<>();
        for (int gap=0 ; gap < 10000 ; gap++) {
            for (int i = 0; i < item.size(); i++) {
                if (item.get(i).counter < minSup) {
                    item.remove(i);
                    break;
                }
            }
        }
        for (int i=0 ; i<item.size() ; i++){
            for (int j=0 ; j<item.get(i).set.size() ; j++){
                s.add(item.get(i).set.get(j));
            }
        }
         //try
        /*
        for (int i=0 ; i<item.size() ; i++){
            for (int j=0 ; j<item.get(i).set.size() ; j++){
                System.out.println(item.get(i).set +" "+ item.get(i).counter);
            }
        }
        */
         //try
        /*for (int i=0 ; i<s.size() ; i++)
            System.out.println(s.get(i));*/
            return s;
    }
    /********************************************/

    public static ArrayList <Vector<String>> first_combnie(Vector<String> v){
        ArrayList <Vector<String>> aray = new ArrayList<>();
        Vector <String> new_V = new Vector<>();

        for (int i=0 ; i < v.size()-1 ; i++){
            for (int j=i+1; j<v.size() ; j++) {
                new_V.add(v.get(i));
                new_V.add(v.get(j));
                aray.add(new_V);
                new_V = new Vector<>();

             }
        }
        /*for (int i=0 ; i <aray.size() ; i++)
           System.out.println(aray.get(i));*/
        return aray;
    }
    /********************************************/

    public static ArrayList <itemset> second_combine(ArrayList <Vector<String>> v , ArrayList<Transactioin> t , ArrayList <itemset> item){
        itemset it = new itemset();
        for (int i=0 ; i<v.size() ; i++) {
            for (int j = 0; j < v.get(i).size(); j++) {
                it.set.add(v.get(i).get(j));
            }
            item.add(it);
            it = new itemset();
        }

        for (int i=0 ; i<t.size() ; i++) {
            for (int j= 0; j<item.size() ; j++){
                    if(t.get(i).value.contains(item.get(j).set.get(0)) && t.get(i).value.contains(item.get(j).set.get(1))){
                        item.get(j).counter = item.get(j).counter +1;
                }
            }
        }
        return item;
    }
    /********************************************/

    public static ArrayList <itemset> third_combine ( ArrayList <itemset> item , ArrayList<Transactioin> t){
        ArrayList <itemset> new_item = new ArrayList<>();
        itemset it = new itemset();

        for (int i=0 ; i<item.size()-1 ; i++) {
            for (int j = i + 1; j < item.size(); j++) {
                if(item.get(i).set.get(0).equals(item.get(j).set.get(0))){
                    it.set.addAll(item.get(i).set);
                    it.set.add(item.get(j).set.get(1));
                    new_item.add(it);
                    it = new itemset();
                    break;
                }
                else if(item.get(i).set.get(0).equals(item.get(j).set.get(1))){
                    it.set.addAll(item.get(i).set);
                    it.set.add(item.get(j).set.get(0));
                    new_item.add(it);
                    it = new itemset();
                    break;
                }
                else {
                    it.set.addAll(item.get(i).set);
                    it.set.add(item.get(j).set.get(0));
                    new_item.add(it);
                    it = new itemset();
                    it.set.addAll(item.get(i).set);
                    it.set.add(item.get(j).set.get(1));
                    new_item.add(it);
                    it = new itemset();
                    break;
                }
            }
        }
        int c = 0;
        for (int i=0 ; i<t.size() ; i++) {
            for (int j = 0; j < new_item.size(); j++) {
                for (int h = 0; h < new_item.get(j).set.size(); h++) {
                    if (t.get(i).value.contains(new_item.get(j).set.get(h))) {
                        ++c;
                        if(c == 3)
                            new_item.get(j).counter = new_item.get(j).counter + 1;
                    }
                }
                c =0;
            }
        }
/*
        for (int i=0 ; i <V.size() ; i++)
            System.out.println(V.get(i));
*/
        return new_item;
    }
    /********************************************/

    public static ArrayList <Vector<String>> build_first_generation (Vector<String> v){
        ArrayList <Vector<String>> aray = new ArrayList<>();
        Vector <String> new_V = new Vector<>();

        for (int i=0 ; i < v.size()-3 ; i++) {
            for (int j = i + 1; j < v.size()-2; j++) {
                for (int h = i + 2; h < v.size()-1; h++) {
                    for (int x = i + 3; x < v.size(); x++) {
                        new_V.add(v.get(i));
                        new_V.add(v.get(j));
                        new_V.add(v.get(h));
                        new_V.add(v.get(x));
                        aray.add(new_V);
                        new_V = new Vector<>();
                    }
                }
            }
        }
/*
        for (int i=0 ; i <aray.size() ; i++)
           System.out.println(aray.get(i));*/
        return aray;
    }
    public static ArrayList <itemset> second_combine_fig(ArrayList <Vector<String>> v , ArrayList<Transactioin> t , ArrayList <itemset> item){
        itemset it = new itemset();
        for (int i=0 ; i<v.size() ; i++) {
            for (int j = 0; j < v.get(i).size(); j++) {
                it.set.add(v.get(i).get(j));
            }
            item.add(it);
            it = new itemset();
        }

        for (int i=0 ; i<t.size() ; i++) {
            for (int j= 0; j<item.size() ; j++){
                if (t.get(i).value.contains(item.get(j).set.get(0))
                        && t.get(i).value.contains(item.get(j).set.get(1))
                        && t.get(i).value.contains(item.get(j).set.get(2))
                        && t.get(i).value.contains(item.get(j).set.get(3)))
                    item.get(j).counter = item.get(j).counter +1;
                }
            }
        return item;
    }
    /********************************************/


    public static ArrayList <Vector<String>> build_second_generation (Vector<String> v) {
        ArrayList<Vector<String>> aray = new ArrayList<>();
        Vector<String> new_V = new Vector<>();

        for (int i = 0; i < v.size() - 4; i++) {
            for (int j = i + 1; j < v.size() - 3; j++) {
                for (int h = i + 2; h < v.size() - 2; h++) {
                    for (int x = i + 3; x < v.size() - 1; x++) {
                        for (int f = i + 4; f < v.size(); f++) {
                            new_V.add(v.get(i));
                            new_V.add(v.get(j));
                            new_V.add(v.get(h));
                            new_V.add(v.get(x));
                            new_V.add(v.get(f));
                            aray.add(new_V);
                            new_V = new Vector<>();
                        }
                    }
                }
            }
        }

/*        for (int i=0 ; i <aray.size() ; i++)
           System.out.println(aray.get(i));*/
            return aray;
    }
    public static ArrayList <itemset> second_combine_sej(ArrayList <Vector<String>> v , ArrayList<Transactioin> t , ArrayList <itemset> item){
        itemset it = new itemset();
        for (int i=0 ; i<v.size() ; i++) {
            for (int j = 0; j < v.get(i).size(); j++) {
                it.set.add(v.get(i).get(j));
            }
            item.add(it);
            it = new itemset();
        }

        for (int i=0 ; i<t.size() ; i++) {
                for (int j = 0; j < item.size(); j++) {
                    if (t.get(i).value.contains(item.get(j).set.get(0))
                            && t.get(i).value.contains(item.get(j).set.get(1))
                            && t.get(i).value.contains(item.get(j).set.get(2))
                            && t.get(i).value.contains(item.get(j).set.get(3))
                            && t.get(i).value.contains(item.get(j).set.get(4)))
                        item.get(j).counter = item.get(j).counter + 1;
                }
            }
        return item;
    }
    /********************************************/


    public static ArrayList <Vector<String>> build_third_generation (Vector<String> v) {
        ArrayList <Vector<String>> aray = new ArrayList<>();
        Vector <String> new_V = new Vector<>();

        for (int i=0 ; i < v.size()-5 ; i++) {
            for (int j = i + 1; j < v.size()-4; j++) {
                for (int h = i + 2; h < v.size()-3; h++) {
                    for (int x = i + 3 ; x < v.size()-2; x++) {
                        for (int x1 = i + 4 ; x1 < v.size()-1 ; x1++) {
                            for (int x2 = i + 5 ; x2 < v.size(); x2++) {
                                new_V.add(v.get(i));
                                new_V.add(v.get(j));
                                new_V.add(v.get(h));
                                new_V.add(v.get(x));
                                new_V.add(v.get(x1));
                                new_V.add(v.get(x2));
                                aray.add(new_V);
                                new_V = new Vector<>();
                            }
                        }
                    }
                }
            }
        }

        /*for (int i=0 ; i <aray.size() ; i++)
           System.out.println(aray.get(i));*/
        return aray;
    }
    public static ArrayList <itemset> second_combine_thg(ArrayList <Vector<String>> v , ArrayList<Transactioin> t , ArrayList <itemset> item){
        itemset it = new itemset();
        for (int i=0 ; i<v.size() ; i++) {
            for (int j = 0; j < v.get(i).size(); j++) {
                it.set.add(v.get(i).get(j));
            }
            item.add(it);
            it = new itemset();
        }

        for (int i=0 ; i<t.size() ; i++) {
            for (int j = 0; j < item.size(); j++) {
                if (t.get(i).value.contains(item.get(j).set.get(0))
                        && t.get(i).value.contains(item.get(j).set.get(1))
                        && t.get(i).value.contains(item.get(j).set.get(2))
                        && t.get(i).value.contains(item.get(j).set.get(3))
                        && t.get(i).value.contains(item.get(j).set.get(4))
                        && t.get(i).value.contains(item.get(j).set.get(5)))
                    item.get(j).counter = item.get(j).counter + 1;
            }
        }

        return item;
    }
    /********************************************/

    public static double calculate_confidence (ArrayList<itemset> a) {
        double y =0 ;


        return y;
    }

    public static int getMax (ArrayList<itemset> a){
        int x=0 ;
        for (int i=0 ; i<a.size() ; i++){
            if(x >= a.get(i).set.size())
                continue;
            else
                x = a.get(i).set.size();
        }
        return x;
    }

    //********************************************/ //MAIN// /********************************************/
    public static void main(String[] args) throws IOException {

        int minsup = 0;
        int NUm_of_records =0;

        // Variables...
        Scanner in = new Scanner(System.in);
        System.out.println("Please Enter The number of records to read : ");
        NUm_of_records = in.nextInt();
        System.out.println("Please Enter The minmum support Value : ");
        minsup = in.nextInt();

        ArrayList <Vector<String>> leaving = new ArrayList<>();
        ArrayList<Transactioin> transactioins = new ArrayList<>();
        ArrayList <itemset> itemsets = new ArrayList<>();
        ArrayList <itemset> itemsets_history = new ArrayList<>();
        Vector<String> used_List = new Vector<>();
        Vector <String> V = new Vector<>();

        // Fill the transactions...
        File file = new File("E:\\Com & inf\\Computer Science level 4\\Data Mining\\Assigns\\src\\CarSales.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        Vector <String> s = new Vector<>();
        for (int i=0 ; i<NUm_of_records ; i++) {
            String x = br.readLine();
            String[] xx = x.split("\\s+");
            Transactioin t = new Transactioin();
            t.id = i+1;
            for (int j=0; j<xx.length ; j++)
                t.value.add(xx[j]);

            transactioins.add(t);
        }

        used_List = get_uniqe(transactioins);
        itemsets = itemSet_builder(used_List,transactioins );
        used_List = chech_minSup(itemsets , minsup);

        for (int j=0; j<itemsets.size() ; j++){
            itemsets_history.add(itemsets.get(j));
        }

        for (int j=1; j<itemsets.size() ; j++)
            itemsets.remove(--j);
            itemsets.remove(0);

        itemsets = second_combine(first_combnie(used_List), transactioins , itemsets);
        chech_minSup(itemsets,minsup );
        for (int j=0; j<itemsets.size() ; j++){
            itemsets_history.add(itemsets.get(j));
        }

        itemsets = third_combine(itemsets , transactioins);
        chech_minSup(itemsets, minsup);
        for (int j=0; j<itemsets.size() ; j++){
            itemsets_history.add(itemsets.get(j));
        }

        V = get_uniqe_items(itemsets);

        //build_four_generation
        for (int j=1; j<itemsets.size() ; j++)
            itemsets.remove(--j);
            itemsets.remove(0);
        second_combine_fig(build_first_generation(V), transactioins, itemsets);
        chech_minSup(itemsets, minsup);
        for (int j=0; j<itemsets.size() ; j++){
            itemsets_history.add(itemsets.get(j));
        }
        V = get_uniqe_items(itemsets);

        for (int j=0; j<itemsets_history.size() ; j++) {
            if (itemsets_history.get(j).set.size() == (getMax(itemsets_history)))
            System.out.println("The most frequant : "+ itemsets_history.get(j).set + " -- S is : " +  (itemsets_history.get(j).counter/transactioins.size()) + " -- C is : "+ count(itemsets_history.get(j).set,transactioins)/(itemsets_history.get(j).counter/transactioins.size()));
        }
    }
}
        /*
        //build_five_generation
        for (int j=1; j<itemsets.size() ; j++)
            itemsets.remove(--j);
            itemsets.remove(0);
        second_combine_sej(build_second_generation(V), transactioins, itemsets);
        chech_minSup(itemsets, minsup);
        for (int j=0; j<itemsets.size() ; j++){
            itemsets_history.add(itemsets.get(j));
        }
        V = get_uniqe_items(itemsets);
        //build_sixx_generation
       for (int j=1; j<itemsets.size() ; j++)
            itemsets.remove(--j);
            itemsets.remove(0);
        second_combine_thg(build_third_generation(V), transactioins, itemsets);
        chech_minSup(itemsets, minsup);
        for (int j=0; j<itemsets.size() ; j++){
            itemsets_history.add(itemsets.get(j));
        }
        */

