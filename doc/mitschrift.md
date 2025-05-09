# 16. April
Aber man müsste entscheiden, was ViewModel eigentlich genau ist.

# 28. April
Backpropagation Algorithmus

```text
Man braucht noch in jedem Knoten ein Feld hasValidChildren und einen entsprechenden Setter.
Die Wurzel ist ein ungelöstes Sudoku. Dies wird zuerst deterministisch gelösten so weit es geht solveByReasoningAsFarAsPossible() . 
Dann wird getestet, ob das Sudoku gelöst ist  isSolved() . Wenn ja, return.

Wenn nein, wird ein Kindknoten erstellt, der zuerst eine Kopie vom Elternknoten ist copy()  addChild(SudokuBoard node) . 
Dann ist dieser Kindknoten also der aktuelle Knoten. 
Zuerst wird geguckt, welche Zelle am wenigsten Kandidaten hat Point findMinimumOfPossibilitiesCell() . 
Dann wird eine dieser Möglichkeiten ausgesucht und ins Sudoku eingefügt try(Point point, int value) . 
Diese Methode speichert auch diesen Tupel in einem record tried(Point, int) . 
Auf einem Knoten kann also auch nur für eine Zelle und einen Wert etwas ausprobiert werden.
Dann wird getestet, ob das Sudoku noch valide ist  isValid() . 
Wenn nein, wird im Elternknoten die Zelle des gerade ausprobierten Punktes ausgewählt und deren Möglichkeit gelöscht deletePossibility(Point point, int value) . 
Dann wird im Elternknoten deterministisch gelöst  solveByReasoningAsFarAsPossible() . 
Dann wird getestet, ob das Sudoku gelöst ist  isSolved() . 
Wenn es nicht gelöst ist, wird ein weiterer Kindknoten im Elternknoten erstellt und dort weiter gemacht.  
[Ich komme zurück zum if(isValid()):] 
Wenn das Sudoku valide ist , wird dem aktuellen Knoten ein Kindknoten hinzugefügt und wir sind dann wieder bei dem Absatz über den Kindknoten.

Man braucht noch in jedem Knoten ein Feld hasValidChildren. 
Das ist dann false, wenn alle möglichen Kinderknoten ausprobiert wurden und jeweils nicht valide waren. 
Wenn das Feld false ist, bedeutet das, dass das gespeichert record tried auch falsch ist. 
Im akutellen Elternknoten muss als die Möglichkeit dafür gelöscht werden  deletePossibility(Point point, int value)  .
```