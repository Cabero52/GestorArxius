# Directori src

> Path absolut: /FONTS/src

## Descripció del directori
Aquest directori conté tot el codi del projecte organitzat per packages

## Elements del directori

- **Directori main:**
Conté tots els codis de les classes del model classificats per directoris, un per cada capa (seguint l'arquitectura
en tres capes). 
El directori domain és l'únic que té contingut.
- **Directori test:**
Conté tots els tests unitaris, fets amb JUnit, de les classes del model conceptual classificats per les classes que
realitzen alguna funcionalitat i les classes que defineixen tipus de dades.
- ***Makefile***
Permet executar el sistema i testejar les classes a partir dels drivers. Conté diverses opcions.

## Descripció Makefile

- Per executar correctament llegir el fitxer InstruccionsCompilar.txt on s'explica els passos que s'han de seguir.