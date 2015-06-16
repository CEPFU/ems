setWindow(countWindow(1));
Match e1;
Match e2;
Match e3;
/*
profile(
		sequence(
			and(e1 = equal("Stations_ID", 10381), forEvent(e1, equal("Niederschlagshoehe", 0.0))),
			and(e2 = equal("Stations_ID", 10381), forEvent(e2, equal("Niederschlagshoehe", 0.0))),
			and(e3 = equal("Stations_ID", 10381), forEvent(e3, equal("Niederschlagshoehe", 0.0)))
		),
		compositeEventNotification(event("Description", "Let's harvest"))
	);
*/
profile(
		sequence(
			equal("Niederschlagshoehe", 0.0),
			equal("Niederschlagshoehe", 0.0),
			equal("Niederschlagshoehe", 0.0)
		),
		compositeEventNotification(event("Description", "Let's harvest"))
	);