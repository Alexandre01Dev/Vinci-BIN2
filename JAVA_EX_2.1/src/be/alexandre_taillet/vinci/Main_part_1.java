package be.alexandre_taillet.vinci;

import be.alexandre_taillet.vinci.domaine.Plat;
import be.alexandre_taillet.vinci.domaine.Plat.Cout;
import be.alexandre_taillet.vinci.domaine.Plat.Difficulte;

import java.util.Iterator;

public class Main_part_1 {

	public static void main(String[] args) {

		Plat plat = null;
		plat = new Plat("Waterzooi", 4, Difficulte.XX, Cout.$$$);

		Instruction instruction = new Instruction("Couper les légumes", 15);
		try {
			plat.insererInstruction(0,instruction);
		} catch(IllegalArgumentException iae){}
		plat.ajouterInstruction(instruction);
		instruction = new Instruction("Faire revenir les légumes", 5);
		plat.ajouterInstruction(instruction);
		instruction = new Instruction("Laisser mijoter jusqu'à cuisson du poulet",50);
		try {
			plat.insererInstruction(4,instruction);
		} catch(IllegalArgumentException iae){}
		plat.ajouterInstruction(instruction);
		instruction = new Instruction("Laisser légèrement refroidir", 3);
		plat.ajouterInstruction(instruction);
		System.out.println(plat.instructions());
		instruction = new Instruction("Ajouter la crème et servir", 0);
		plat.ajouterInstruction(instruction);
		System.out.println(plat.instructions());
		instruction = new Instruction("Laisser mijoter jusqu'à cuisson du poulet", 67);
		plat.remplacerInstruction(3,instruction);
		instruction = new Instruction("Ajouter le poulet", 0);
		plat.insererInstruction(3,instruction);
		System.out.println(plat.instructions());
		plat.supprimerInstruction(5);

		System.out.println(plat.instructions());
		Iterator<Instruction> instructionIterator = plat.instructions().iterator();
		while(instructionIterator.hasNext()){
			instruction = instructionIterator.next();
		}
		try{
			instructionIterator.remove();
		} catch (UnsupportedOperationException uoe){}

		System.out.println(plat.instructions());
		System.out.println(plat);
	}

}
