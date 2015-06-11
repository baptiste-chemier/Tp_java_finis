package Modele;

import java.util.ArrayList;

public abstract class ModeleGrille {
    protected int nbCases[]; // 0 > X ; 1 > Y ; 2 > Z ; 3 > ...
    protected ModeleCase[] cases;
    protected int nbBombes;
    protected int nbBombesRestantes;
    protected int derniereCase;
    protected int nbCasesDecouvertes;
    protected int nbCasesTotales;
    
    public ModeleGrille(int _nbCases[], int _nbBombes){
        nbCases = _nbCases;
        nbBombes = _nbBombes;
        nbBombesRestantes = _nbBombes;
        derniereCase = -1;
        nbCasesDecouvertes = 0;   
    }  
         
    public abstract ArrayList<ModeleCase> getCasesVoisines(ModeleCase courante);    
        
    public void setBombes(int nombre, int caseInvulnerable){
        if(nombre>=getCases().length-1){
            for(int id=0; id<getNbCasesTotales(); id++){
                if(id!=caseInvulnerable)
                    getCases()[id].setPiege(true);
            }
            return;
        }
        int id;
        while(nombre>0){
            id = nombreAleatoire(0, getNbCasesTotales());
            if(!cases[id].isPiege() && id!=caseInvulnerable){
                getCases()[id].setPiege(true);
                nombre--;
            }
        }
    }  

    public void propagationVoisins(ModeleCase courante){
        if(courante.isPiege() && !courante.isDecouverte()){
            return;
        }
        ArrayList<ModeleCase> caseVoisines = this.getCasesVoisines(courante);
        courante.setDecouverte(true);
        setNbCasesDecouvertes(getNbCasesDecouvertes() + 1);
        if(this.getNombreVoisinsPieges(courante)==0)
        for(int i=0; i<caseVoisines.size(); i++){
            if(!caseVoisines.get(i).isDecouverte())
                propagationVoisins(caseVoisines.get(i));
        }
    }    

    public int getNombreVoisins(ModeleCase courante){
        return this.getCasesVoisines(courante).size();
    }

    public ArrayList<ModeleCase> getVoisinsPieges(ModeleCase courante){
        ArrayList<ModeleCase> voisinsPieges = new ArrayList(); 
        ArrayList<ModeleCase> caseVoisines = this.getCasesVoisines(courante);
        for(int i=0; i<caseVoisines.size(); i++){
            if(caseVoisines.get(i).isPiege())
                voisinsPieges.add(caseVoisines.get(i));
        }
        return voisinsPieges;
    } 
 
    public int getNombreVoisinsPieges(ModeleCase courante){
        return this.getVoisinsPieges(courante).size();
    }  

    public int[] getNbCases() {
        return nbCases;
    }

    public ModeleCase[] getCases() {
        return cases;
    }

    public int getNbBombes() {
        return nbBombes;
    }

    public boolean isPremierCoup() {
        return getDerniereCase()!=-1;
    }

    public void setDerniereCase(int caseImmunise) {
        this.derniereCase = caseImmunise;
    }

    public int getNbCasesDecouvertes() {
        return nbCasesDecouvertes;
    }

    public int getDerniereCase() {
        return derniereCase;
    }

    public int getNbBombesRestantes() {
        return nbBombesRestantes;
    }

    public void setNbBombesRestantes(int nbBombesRestantes) {
        this.nbBombesRestantes = nbBombesRestantes;
    }

    public void setNbCasesDecouvertes(int nbCasesDecouvertes) {
        this.nbCasesDecouvertes = nbCasesDecouvertes;
    }
    
    private int nombreAleatoire(int min, int max){
        return (int) (Math.random() * (max + min)) + min;
    }

    public int getNbCasesTotales() {
        return nbCasesTotales;
    }  
}