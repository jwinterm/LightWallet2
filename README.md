# LightWallet GUI
This is a GUI frontend that runs on top of simplewallet (in RPC mode). It is designed to work with a remote bitmonerod node by default, so that it can function as a lightweight wallet for most users. By default it uses Atrides open node at http://node.moneroworld.com:18081, but you can change the node it uses to a local node during setup or at any launch; to use a local bitmonerod instance set it as http://localhost:18081. 

**This program is an alpha version, and although I don't think there is really any way to screw up your wallet, please exercise some modicum of caution.**

## Installation
### Windows, Linux, and Mac
The requirements for running the program using the jar file release is a java virtual machine installed v1.7+, and monero-wallet-cli v0.10+ in the same directory. If you also want to run a local daemon you'll monerod.exe/monerod binary. The wallet and daemon files can be downloaded here: https://github.com/monero-project/bitmonero/releases

If you're using the binary LightWallet.exe, you still need to have monero-wallet-cli.exe in the same directory. 

## Running
It should be OK to launch the program with an instance of monerod and/or monero-wallet-cli running. 

On the initial run, it will prompt you to create a new wallet or to import one by typing/copying in the path of the keys file or importing a 25 word mnemonic seed. This will create several files in the same directory as LightWallet and monero-wallet-cli, including lightwallet.conf. If you ever want to create or import a new wallet, simply delete lightwallet.conf.

## Known issues
If you manage to crash the program, you will probably get an instance of monero-wallet-cli(.exe) hanging around, so to get rid of it you will need to go into the task manager processes tab and end the monero-wallet-cli process on windows, "ps -A | grep monero-wallet-cli" to get PID and then "kill PID" or "killall monero-wallet-cli" on linux, or restart your computer.

## Future Work
Transaction history has been disabled for the time being. If you want to parse your tx history check monero-wallet-cli.log in the same directory.
