![image of Tycho sculpture](http://thomasheidtmann.de/img/160501_TYCHO_Image_01.jpg "Tycho, an interactive object")

[Video of Tycho in action](https://vimeo.com/164922524)

Tycho Setup Guide
=================

Hardware
--------

// TODO : instructions for hanging and connecting hardware


Software
--------

1. connect all hardware first, as described above
2. run `OPC/fcserver-osx`
  - test by going to http://localhost:7890/
  - you should see **Fadecandy LED Controller** under Connected Devices
    - If you see **No devices are connected!**, check connection between computer and Fadecandy
  - use the **Test Patterns** dropdown menu to test that LEDs are working properly
    - If Fadecandy is connected, but LEDs do not light, check that LEDs have power and are connected to Fadecandy
3. run `Tycho_LED`
  - LEDs should start flowing
  - // TODO: potential issues:
    - camera not connected
    - camera named incorrectly
4. open MadMapper using `Resources/MadMapper/Tycho.mad`
  - perform remapping as needed
5. run `Tycho_Projection`
  - ripples should now appear when LEDs reach surface, and video footage should change periodically
  - // TODO: potential issues:
    - ...

### Software Pieces

- `OPC/fcserver-osx` - manages communication between computer and Fadecandy/LEDs
- `Tycho_LED` - Processing program, runs camera and LEDs, tells `Tycho_Projection` when to create ripples
- `Tycho_Projection` - Processing program, generates coloured ripples and manages fading between presets in MadMapper
- `MadMapper` - projection mapping software, plays video footage and overlays ripples sent from `Tycho_Projection`
