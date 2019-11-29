package geneticalgorithmminimumfunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 *
 * @author MMukhtarDwiPutra(1301170278)
 */
public class GeneticAlgorithmMinimumFunction {
    static Populasi generateKromosom(int jumlahIndividu,int panjangKromosom){
        Random random = new Random();
        Populasi pop = new Populasi();
        List<Integer> kromosom;
        for (int i = 0; i < jumlahIndividu; i++) {
            kromosom = new ArrayList<>();
            for (int j = 0; j < panjangKromosom; j++) {
                kromosom.add(random.nextInt(10));
            }
            pop.addKromosom(kromosom);
        }
        return pop;
    }
    
    static double dekodeKromosom(List<Integer> kromosom, int rMin, int rMax){
        double x = 0;
        double rumusDekodeBawah = 0;
        double rumusDekodeAtas = 0;
        for (int i = 0; i < kromosom.size(); i++) {
            rumusDekodeBawah = Math.pow(10, -(i+1)) + rumusDekodeBawah;
            rumusDekodeAtas = (kromosom.get(i) * Math.pow(10, -(i+1))) + rumusDekodeAtas;
        }
        rumusDekodeBawah = rumusDekodeBawah * 9;
        x = rMin +(((rMax-rMin) / rumusDekodeBawah)*rumusDekodeAtas);
        return x;
    }
    
    static double nilaiFitness(double x1,double x2){
        double fitness = -nilaiFungsi(x1,x2);
        return fitness;
    }
    
    static double nilaiFungsi(double x1,double x2){
        double fungsi = ((4 - (2.1 * Math.pow(x1,2)) + (Math.pow(x1,4)/3)) * Math.pow(x1,2)) + (x1 * x2) + ((-4 + (4 * Math.pow(x2,2)))*Math.pow(x2,2));
        return fungsi;
    }
    
    static List<Integer> tournamentSelection(Populasi pop, int panjangTournament){
        List<Integer> idxTemp = new ArrayList<>();
        for (int i = 0; i < pop.getListSize(); i++) {
            idxTemp.add(i);
        }
        
        Collections.shuffle(idxTemp);
        
        List<Integer> idxSample = new ArrayList<>();
        for (int i = 0; i < panjangTournament; i++) {
            idxSample.add(idxTemp.get(i));
        }
        
        int idxKromosom2;
        List<Double> fitnesses = new ArrayList<>();
        List<Integer> kromosomX1 = new ArrayList<>();
        List<Integer> kromosomX2 = new ArrayList<>();
        double x1,x2;
        for (int idxSampleKromosom = 0; idxSampleKromosom < idxSample.size(); idxSampleKromosom++){
            idxKromosom2 = pop.getKromosom(idxSampleKromosom).size()/2;
            kromosomX1.clear();
            kromosomX2.clear();
            for (int idxKromosom1 = 0; idxKromosom1 < pop.getKromosom(idxSampleKromosom).size()/2; idxKromosom1++){
                kromosomX1.add(pop.getKromosom(idxSample.get(idxSampleKromosom)).get(idxKromosom1));
                kromosomX2.add(pop.getKromosom(idxSample.get(idxSampleKromosom)).get(idxKromosom2));
                idxKromosom2++;
            }
            x1 = dekodeKromosom(kromosomX1, rMinX1, rMaxX1);
            x2 = dekodeKromosom(kromosomX2, rMinX2, rMaxX2);
            fitnesses.add(nilaiFitness(x1,x2));
        }
        
        mergedListFitness mergeList = new mergedListFitness(idxSample, fitnesses, panjangTournament);
        mergeList.sort("idxSample");
        
        List<Integer> idxParent = new ArrayList<>();
        idxParent.add(mergeList.getIdxSample(0));
        idxParent.add(mergeList.getIdxSample(1));
        return idxParent;
    }
    
    static List<List<Integer>> crossover(List<Integer> kromosom1, List<Integer> kromosom2, double pC){
        List<List<Integer>> tmpKromosom = new ArrayList<>();
        Random random = new Random();
        double prob = random.nextDouble();
        int point = 0;
        List<Integer> tmpKromosom1 = new ArrayList<>();
        List<Integer> tmpKromosom2 = new ArrayList<>();
        while(point == 0){
            point = random.nextInt(kromosom1.size()-1);
        }
        if(prob<=pC){
            for (int i = 0; i < point; i++){
                tmpKromosom1.add(kromosom1.get(i));
                tmpKromosom2.add(kromosom2.get(i));
            }
            for (int i = point; i < kromosom1.size(); i++){
                tmpKromosom1.add(kromosom2.get(i));
                tmpKromosom2.add(kromosom1.get(i));
            }
            tmpKromosom.add(tmpKromosom1);
            tmpKromosom.add(tmpKromosom2);
            return tmpKromosom;
        }
        tmpKromosom.add(kromosom1);
        tmpKromosom.add(kromosom2);
        return tmpKromosom;
    }
    
    static List<Integer> mutasi(List<Integer> kromosom, double pM, int maxNilaiKromosom){
        Random random = new Random();
        double prob;
        int tmp;
        List<Integer> kromosomBaru = new ArrayList<>();
        for (int i = 0; i < kromosom.size(); i++) {
            prob = random.nextDouble();
            if(prob <= pM){
                tmp = kromosom.get(i);
                while(tmp == kromosom.get(i)){
                    tmp = random.nextInt(maxNilaiKromosom);
                }
                kromosomBaru.add(tmp);
            }else{
                kromosomBaru.add(kromosom.get(i));
            }
        }
        return kromosomBaru;
    }
    
    static List<Integer> steadyState(int jumlahGenerasi, Populasi populasi, int jumlahIndividu, int panjangTournament){
        List<List<Integer>> gabungan = new ArrayList<>();
        List<List<Integer>> child = new ArrayList<>();
        List<List<Integer>> tmpPopulasi = new ArrayList<>();
        List<List<Integer>> tmpAnak = new ArrayList<>();
        List<Double> fitnesses = new ArrayList<>();
        List<Integer> idxParent = new ArrayList<>();
        List<Integer> tmpAnak1 = new ArrayList<>();
        List<Integer> tmpAnak2 = new ArrayList<>();
        List<Integer> anak1 = new ArrayList<>();
        List<Integer> anak2 = new ArrayList<>();
        Populasi pop = new Populasi();
        
        mergedListFitness mergeList;
        
        double x1,x2,nilaiFitness;
        int t, jumlah;
        for (int i = 0; i < jumlahGenerasi; i++) {
            child.clear();
            for (int j = 0; j < populasi.getListSize()/2; j++){
                //Seleksi parent
                idxParent.clear();
                idxParent = tournamentSelection(populasi, panjangTournament);
                anak1 = populasi.getKromosom(idxParent.get(0));
                anak2 = populasi.getKromosom(idxParent.get(1));
                
                //Crossover
                tmpAnak.clear();
                tmpAnak = crossover(anak1, anak2, pC);
                anak1 = tmpAnak.get(0);
                anak2 = tmpAnak.get(1);
                
                //Mutasi
                tmpAnak1 = mutasi(anak1, pM, maxNilaiKromosom);
                tmpAnak2 = mutasi(anak2, pM, maxNilaiKromosom);
                anak1 = tmpAnak1;
                anak2 = tmpAnak2;
                
                child.add(anak1);
                child.add(anak2);
            }
            gabungan.clear();
            gabungan.addAll(populasi.getKromosomList());
            gabungan.addAll(child);
            
            fitnesses.clear();
            for (int j = 0; j < gabungan.size(); j++) {
                x1 = dekodeKromosom(gabungan.get(j).subList(0, gabungan.get(j).size()/2), rMinX1, rMaxX1);
                x2 = dekodeKromosom(gabungan.get(j).subList(gabungan.get(j).size()/2, gabungan.get(j).size()), rMinX2, rMaxX2);
                fitnesses.add(nilaiFitness(x1, x2));
            }
            mergeList = new mergedListFitness(gabungan, fitnesses, gabungan.size(), "gabungan");
            mergeList.sort("gabungan");
            
            pop.getKromosomList().clear();
            tmpPopulasi.clear();
            t = 0;
            jumlah = 0;
            
            for (int j = 0; j < mergeList.getGabungan().size(); j++){
                if(j == 0){
                    pop.addKromosom(mergeList.getGabungan(j));
                    jumlah++;
                }else{
                    x1 = dekodeKromosom(mergeList.getGabungan(j).subList(0, mergeList.getGabungan(j).size()/2), rMinX1, rMaxX1);
                    x2 = dekodeKromosom(mergeList.getGabungan(j).subList(mergeList.getGabungan(j).size()/2, mergeList.getGabungan(j).size()), rMinX2, rMaxX2);
                    nilaiFitness = nilaiFitness(x1, x2);
                    if(nilaiFitness == mergeList.getFitness(jumlah-1)){
                        tmpPopulasi.add(gabungan.get(j));
                        t++;
                    }else{
                        pop.addKromosom(mergeList.getGabungan(j));
                        jumlah++;
                    }
                }
                if(jumlah==jumlahIndividu){
                    break;
                }
            }
            
            int z = 0;
            while(jumlah < jumlahIndividu){
                pop.addKromosom(tmpPopulasi.get(z));
                z++;
                jumlah++;
            }
            
            populasi.getKromosomList().clear();
            populasi.getKromosomList().addAll(pop.getKromosomList());
        }
        return populasi.getKromosom(0);
    }
    
    static int jumlahGenerasi = 100;
    static int jumlahIndividu = 30;
    static int panjangKromosom = 6;
    static int rMinX1 = -3;
    static int rMaxX1 = 3;
    static int rMinX2 = -2;
    static int rMaxX2 = 2;
    static int panjangTournament = jumlahIndividu/2;
    static double pC = 0.70;
    static double pM = 0.01;
    static int maxNilaiKromosom = 10;
    
    public static void main(String[] args){
        Populasi pop = new Populasi();
        List<Integer> kromosomUnggul = new ArrayList<>();
        
        pop = generateKromosom(jumlahIndividu, panjangKromosom);
        kromosomUnggul = steadyState(jumlahGenerasi, pop, jumlahIndividu, panjangTournament);
        double x1 = dekodeKromosom(kromosomUnggul.subList(0, kromosomUnggul.size()/2), rMinX1, rMaxX1);
        double x2 = dekodeKromosom(kromosomUnggul.subList(kromosomUnggul.size()/2, kromosomUnggul.size()), rMinX2, rMaxX2);
        
        System.out.println("Hasil kromosom terbaik : "+kromosomUnggul);
        System.out.println("Nilai X1 : "+x1);
        System.out.println("Nilai X2 : "+x2);
        System.out.println("Nilai fitness "+nilaiFitness(x1,x2));
        System.out.println("Nilai fungsi : "+nilaiFungsi(x1,x2));
    }
}