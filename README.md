# ATC Simulator

Air traffic simulation with departure sequencing, written in Java AWT with no external libraries.

Built for the Object-Oriented Programming 2 course at the School of Electrical Engineering, University of Belgrade.

## What it is

You load a set of airports and a flight schedule, hit start, and the day plays out at 600x speed — one real second is ten simulated minutes. Planes take off, fly across the map, and land.

The part that makes it more than an animation is that flights can't all leave when they want to. Each airport allows one departure per ten-minute window. If three flights are scheduled to leave Belgrade between 00:30 and 00:35, only the first one goes. The others get held on the ground and pushed into the next free slots — 00:40, then 00:50 — and that delay shifts everything scheduled behind them.

I never wrote any queueing logic. The separation rule produces the queue on its own, and watching that happen for the first time was the most interesting thing that came out of the project.

## What you can see here

**A departure sequencer.** The core of the project. It takes a schedule, walks it chronologically, and assigns every flight a legal takeoff slot, holding aircraft on the ground when their airport is already busy. It's greedy and first-come-first-served, and it knows nothing about the screen — it hands back a list of aircraft with resolved departure times and the renderer asks it, every frame, which ones are currently in the air.

**A map that redraws itself sixty times a minute.** Airports appear as labelled squares placed by coordinate. Click one and it blinks red. Checkboxes down the side control which airports are visible, which starts mattering once there are twenty of them. Everything rescales when you resize the window.

**Hand-written CSV and JSON parsers.** No Jackson, no Gson. Both formats read and write. Every way a file can be malformed — wrong columns, duplicate airport code, coordinate out of range, bad time format — produces a message that says what specifically broke and how to fix it, rather than a stack trace.

**An inactivity timer that knows when to shut up.** The app closes 60 seconds after the last user action, with a countdown dialog in the final five. It has to pause while an airport is selected or a simulation is running. That sounds trivial and was the fiddliest part of the entire project.

**No dependencies at all.** The map projection, the renderer, the tables, the forms, the dialogs, the parsers, the animation loop — all of it is built directly on `java.awt`. There are no Swing components anywhere. The only thing I borrowed is `javax.swing.Timer` for tick scheduling.

## How it's put together

Six packages, and the dependency arrows only point one way. The model has no idea a GUI exists — it doesn't import a single AWT class. The renderer depends on the model, never the reverse.

- **`airport`** — the model. Airports, flights, coordinates, time conversion, and typed exceptions for domain violations like a duplicate airport code.
- **`map`** — the sequencer, the aircraft, the coordinate projection, the renderer, the simulation clock, and the controls.
- **`parserExport`** — reading and writing CSV and JSON.
- **`inputFormInterface`** — data entry forms, validation, and the live tables.
- **`backgroundTimer`** — the inactivity watchdog.
- **`mainUserInterface`** — the window and the entry point.

## Three decisions I'd defend

**A plane's position is computed from the current time, not accumulated between frames.** Ask an aircraft where it is at time *t* and it interpolates between its two airports based on when it took off and how long the flight lasts. Nothing mutates per tick. This is the reason pause, resume, and restart were easy — you change what time you hand the renderer and everything stays consistent. Had I incremented coordinates every frame instead, restart would have meant carefully unwinding state, and long runs would have slowly drifted out of sync.

**Exactly one class converts coordinates to pixels.** Everything else works in logical space, in degrees. Before I enforced that, I had a bug where planes flew off the edge of the screen for no visible reason — pixel values were being fed into code that expected degrees. Centralizing the conversion made an entire category of bug impossible rather than just fixing that one instance of it.

**Errors are exceptions, not strings.** The model throws typed exceptions when something is wrong with the data. They get caught at the UI boundary and turned into dialogs there. The model never knows a dialog happened.

## Running it

Any JDK will do — AWT has shipped with Java since version 1.0. Compile the sources and run `mainUserInterface.MainFrame`.

Two sample datasets are included: 16 international airports and 27 flights, including the deliberately conflicting Belgrade departures that make the sequencer visibly do its job. Load either one from the file form inside the app.

## What I'd add next

Separation distances that vary by aircraft size, the way real wake turbulence rules work. Conflict detection between aircraft already in the air. Weather zones that force planes to reroute mid-flight. Great-circle paths instead of straight lines. And tests — the sequencer and the parsers are both pure functions and there's no excuse for them not being covered.

## License

MIT.
