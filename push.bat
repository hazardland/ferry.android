@echo off
IF %1.==. GOTO COMMENT
cd ./game
git add *
git commit -a -m %1
git push origin master
cd ../
git add *
git commit -a -m %1
git push origin master
GOTO END
:COMMENT
echo no commit comment
:END