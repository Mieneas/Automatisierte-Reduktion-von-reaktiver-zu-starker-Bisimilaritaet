# Automatisierte Reduktion von reaktiver zu starker Bisimilarität

Das Repository enthält alle Dateien, die von meiner [Bachelorarbeit](https://github.com/Mieneas/Automatisierte-Reduktion-von-reaktiver-zu-starker-Bisimilaritaet/blob/main/thesis/thesis.pdf) abhängen.
Mehrere Informationen kann in meiner Arbeit an sich gefunden werden.

Meine Arbeit ist basiert hauptsächlich auf die Arbeit von [Maximilian Pohlmann](https://maxpohlmann.github.io/Reducing-Reactive-to-Strong-Bisimilarity/thesis.pdf).

## Abschnitt Abstrakt

Diese Arbeit stellt ein Werkzeug vor, das die reaktive Bisimilarität [[vG20]](https://arxiv.org/abs/2008.11499) auf
beschriftete Transitionssysteme mit Timeouts (LTSt) [[vG21a]](https://doi.org/10.23638/LMCS-17(2:11)2021) überprüft.
Das Werkzeug ist anhand einer von mir automatisierten Reduktion von reaktiver zu starker Bisimilarität [[Poh21]](https://maxpohlmann.github.io/Reducing-Reactive-to-Strong-Bisimilarity/thesis.pdf) und ein Tool-Set von dem mCRL2-
Projekt [[mCRL19]](https://link.springer.com/book/10.1007/978-3-030-17465-1) entwickelt. Es können ein Paar oder alle Paare von Prozessen des eingegebenen LTSt
auf eine reaktive Bisimilarität überprüft werden. Außerdem implementiere ich in
dieser Arbeit eine Verbesserungsmöglichkeit bezüglich der Komplexität des Algorithmus der Reduktion. Die Komplexität ist vom exponentiellen Aufwand auf
linearen vermindert. Die Verbesserung des Algorithmus ermöglicht eine schnellere und effiziente Überprüfung von großen Systemen, bei denen die ursprüngliche
Version nur mit sehr langer Ausführungszeit auskommt.

## Repository-Dateien

- `thesis/document/thesis.pdf` Ist die Bachelorarbeit.
- `thesisi/dokument/thesis.zip` Ist der Sourcecode meiner Bachelorarbeit.
- `thesis/research_colloquium_presentation/Automatisierte Reduktion von reaktiver zu starker Bisimilarität.pdf` Ist eine Forschungskolloquiumspräsentation über meine Bachelorarbeit.
- `thesis/research_colloquium_presentation/Automatisierte Reduktion von reaktiver zu starker Bisimilarität.pptx` Ist der Sourcecode meiner Forschungskolloquiumspräsentation.
- `lts_t2lts/src` Ist der Sourcecode meines Werkzeuges zur Überprüfung der reaktiven Bisimilarität.
- `code_systems/lts_t_files` Ist nur ein Hilfsordner gebraucht bei `lts_t2lts/src`.
- `all_in_one` Dadurch kann mein Werkzeug leicht angewandt werden. (Siehe den Abschnitt "Verwendungsweise" meiner [Bachelorarbeit](https://github.com/Mieneas/Automatisierte-Reduktion-von-reaktiver-zu-starker-Bisimilaritaet/blob/main/thesis/document/thesis.pdf)).
