#gitSecretsScan.sh
#!/bin/sh
git ls-files | git secrets --scan