/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithmminimumfunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class mergedListFitness {
    private List<List<Integer>> gabungan = new ArrayList<>();
    private List<Double> fitnesses = new ArrayList<>();
    private List<Integer> idxSample = new ArrayList<>();

    public mergedListFitness(List<List<Integer>> gabungan, List<Double> fitnesses, int panjangList, String populasi){
        this.gabungan = gabungan;
        for (int i = 0; i < panjangList; i++) {
            this.fitnesses.add(fitnesses.get(i));
        }
    }

    public mergedListFitness(List<Integer> idxSample, List<Double> fitnesses, int panjangList){
        for (int i = 0; i < panjangList; i++){
            this.idxSample.add(idxSample.get(i));
            this.fitnesses.add(fitnesses.get(i));
        }
    }

    public List<Integer> getGabungan(int i){
        return this.gabungan.get(i);
    }

    public List<List<Integer>> getGabungan(){
        return this.gabungan;
    }

    public int getIdxSample(int i){
        return this.idxSample.get(i);
    }

    public List<Integer> getIdxSample(){
        return this.idxSample;
    }

    public double getFitness(int i){
        return this.fitnesses.get(i);
    }

    public List<Double> getFitness(){
        return this.fitnesses;
    }

    public void sort(String type){
        if("idxSample".equals(type)){
            int n = fitnesses.size();
            int tempIdx;
            double tempFitness;
            for (int j = 1; j < n; j++) {  
                tempFitness = fitnesses.get(j); 
                tempIdx = idxSample.get(j);
                int i = j-1;
                while ((i > -1) && (fitnesses.get(i) > tempFitness)){
                    fitnesses.set(i+1, fitnesses.get(i));
                    idxSample.set(i+1, idxSample.get(i));
                    i--;
                }  
                fitnesses.set(i+1, tempFitness);
                idxSample.set(i+1, tempIdx);
            }
            Collections.reverse(fitnesses);
            Collections.reverse(idxSample);
        }else if(type.equals("gabungan")){
            int n = fitnesses.size();
            List<Integer> tempGab;
            double tempFitness;
            for (int j = 1; j < n; j++) {  
                tempFitness = fitnesses.get(j); 
                tempGab = gabungan.get(j);
                int i = j-1;
                while ((i > -1) && (fitnesses.get(i) > tempFitness)){
                    fitnesses.set(i+1, fitnesses.get(i));
                    gabungan.set(i+1, gabungan.get(i));
                    i--;
                }  
                fitnesses.set(i+1, tempFitness);
                gabungan.set(i+1, tempGab);
            }
            Collections.reverse(gabungan);
            Collections.reverse(fitnesses);
        }
    }
}