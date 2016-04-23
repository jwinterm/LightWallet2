# LightWallet GUI
This is a GUI frontend that runs on top of simplewallet (in RPC mode). It is designed to work with a remote bitmonerod node by default, so that it can function as a lightweight wallet for most users. By default it uses Atrides open node at http://node.moneroclub.com:8880, but you can change the node it uses to a local node during setup or at any launch; to use a local bitmonerod instance set it as http://localhost:18081. 

**This program is an alpha version, and although I don't think there is really any way to screw up your wallet, please exercise some modicum of caution.**

## Installation
### Windows, Linux, and Mac
The requirements for running the program using the jar file release is a java virtual machine installed v1.7+, and simplewallet v0.9+ in the same directory (on Windows you need simplewallet.exe, libeay32.dll, libwinpthread-1.dll, and ssleay32.dll). If you also want to run a local daemon you'll bitmonerod.exe/bitmonerod binary. The simplewallet and bitmonerod files can be downloaded here: https://github.com/monero-project/bitmonero/releases

If you're using the binary LightWallet.exe, you still need to have simplewallet.exe and the dll files in the same directory. 

## Running
It should be OK to launch the program with an instance of bitmonerod and/or simplewallet running. 

On the initial run, it will prompt you to create a new wallet or to import one by typing/copying in the path of the keys file or importing a 25 word mnemonic seed. This will create several files in the same directory as LightWallet and simplewallet, including lightwallet.conf. If you ever want to create or import a new wallet, simply delete lightwallet.conf.

## Known issues
If you manage to crash the program, you will probably get an instance of simplewallet(.exe) hanging around, so to get rid of it you will need to go into the task manager processes tab and end the simplewallet process on windows, "ps -A | grep simplewallet" to get PID and then "kill PID" or "killall simplewallet" on linux, or restart your computer.

## Future Work
There seems to sometimes be an issue with properly tallying all previous transactions. This is shown in the wallet tab as calculated balance. I would like to make sure that this always matches total/locked balance and all incoming/outgoing transactions are properly accounted for.
