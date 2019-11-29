/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithmminimumfunction;

import java.util.ArrayList;
import java.util.List;

public class Populasi {
    private List<List<Integer>> kromosom = new ArrayList<>();
    public void addKromosom(List<Integer> krom){
        kromosom.add(krom);
    }
    public List<Integer> getKromosom(int i){
        return this.kromosom.get(i);
    }
    public List<List<Integer>> getKromosomList(){
        return this.kromosom;
    }
    public int getAlel(int idxKromosom, int idxAlel){
        return this.kromosom.get(idxKromosom).get(idxAlel);
    }
    public int getListSize(){
        return this.kromosom.size();
    }
}