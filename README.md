# Automatisierte Reduktion von reaktiver zu starker Bisimilarität

Das Repository enthält alle Dateien, die von meiner [Bachelorarbeit](https://github.com/Mieneas/Automatisierte-Reduktion-von-reaktiver-zu-starker-Bisimilaritaet/blob/main/thesis/thesis.pdf) abhängen.
Mehrere Informationen kann in meiner Arbeit an sich gefunden werden.

Meine Arbeit ist basiert hauptsächlich auf die Arbeit von [Maximilian Pohlmann](https://maxpohlmann.github.io/Reducing-Reactive-to-Strong-Bisimilarity/thesis.pdf).

## Abschnitt Abstrakt

Diese Arbeit stellt ein Werkzeug vor, das die reaktive Bisimilarität [[vG20]](https://arxiv.org/abs/2008.11499) auf
beschriftete Transitionssysteme mit Timeouts (LTSt) [[vG21a]](https://doi.org/10.23638/LMCS-17(2:11)2021) überprüft.
Das Werkzeug ist anhand einer Reduktion von reaktiver zu starker Bisimilarität [[Poh21]](https://maxpohlmann.github.io/Reducing-Reactive-to-Strong-Bisimilarity/thesis.pdf) entwickelt. Das Werkzeug kann ein Paar oder alle Paare von
Prozessen des eingegebenen LTSt auf reaktive Bisimilarität überprüfen. Außerdem implementiere ich in dieser Arbeit eine Verbesserungsmöglichkeit bezüglich der Komplexität des Algorithmus der Reduktion. Die Komplexität ist
von m · (1 + 2|n|) auf m · (n + t + 1) vermindert. Die Effizienz kann deutlich
bemerkbar sein, wenn die beiden Versionen auf große Systeme umgesetzt werden. Diese Verbesserung ermöglicht eine schnellere und effiziente Überprüfung
von großen Systemen, mit denen die ursprüngliche Version nur mit sehr langer

## Repository-Dateien

- `thesis/thesis.pdf` Ist die Bachelorarbeit
- `thesisi/thesis.zip` Ist der Sourcecode meiner Bachelorarbeit
- `lts_t2lts/src` Ist der Sourcecode meines Werkzeuges zur Überprüfung der reaktiven Bisimilarität
- `code_systems/lts_t_files` Ist nur ein Hilfsordner gebraucht bei `lts_t2lts/src`.
- `all_in_one` Dadurch kann mein Werkzeug leicht angewandt werden. (Siehe den Abschnitt "Verwendungsweise" meiner [Bachelorarbeit](https://github.com/Mieneas/Automatisierte-Reduktion-von-reaktiver-zu-starker-Bisimilaritaet/blob/main/thesis/thesis.pdf))
