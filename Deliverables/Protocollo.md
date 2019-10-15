# SOCKET PROTOCOL


## REGISTRATION

**to client**

SignIn \n Insert username \n Insert password CR

**to server**

username \n
password CR


## LOGIN

**to client**

login\n 
inserire passwordCR

**to server**

login\n
username\n
passwordCR
 
**to client**

logged [username]CR


## SET PUBLIC OBJECTIVE CARDS

**to client**

PublicObjectiveCards\n[Card1]\n[Card2]\n[Card3]CR

**to server**

Public objectives Received [username]CR


## SET TOOL CARDS

**to client** 

Toolcards\n[ToolCard1]\n[ToolCard2]\n......[ToolCard12]CR

**to server**

Toolcards Received [username]CR


## PRIVATE CARD

**to client**

PrivateObjectiveCard\n[Color]CR

**to server**

PrivateObjectiveCard Received [username]CR


## CHOOSE PATTERN

**to client** 

Pattern\n
Choose Pattern\n
[Pattern1]\n[Pattern2]\n[Pattern3]\n[Pattern4]CR

**to server**

Pattern\n[Pattern]CR

**to client**

OK [username]CR


## PLACEDIE

**to client**

Play [username]CR

**to server**

PlaceDie\n[color]\n[number]\n[patt.X]\n[patt.Y]CR

**to client**

OK [username]CR

([Exception Message] [username]CR)		_See enum of messages_


## TOOLCARD

**to server**

ToolCard\n[ToolCardX]CR  

**to client**

OK [username]CR
(NO [username]CR)

_NB:_
_At the end of all actions, server send to client a string that contains the model of the new dice distribution. <DashBoard>_
_The structure of this path is all the same: "<Used(0,0),Color(0,0),Value(0,0)><TAB><TAB><Used(0,1),Color(0,1),Value(0,1)><TAB>....<Used(3,4),Color(3,4),Value(3,4)>CR"_
_It describes the dashboard**_

### TOOLCARD1

**to server**

[die.x] [die.y] [add(or dec)]CR

**to client**

OK [username]\n[DashBoard]CR

([Exception Message] [username]CR)

### TOOLCARD2

**to server**

[die.x] [die.y] [to.x] [to.y]CR

**to client**

OK [username]\n[DashBoard]CR

([Exception Message] [username]CR)

...
...

all the toolcards

...
...


## ENDTURN

**to server**

EndTurnCR

**to client**

OK [username]CR


## ENDGAME

**to client**

EndGame\n[username1] [punteggio1]\n[username2] [punteggio2]\n[username3] [punteggio3]\n[username4] [punteggio4]CR


## EXCEPTION

**to client**

ExceptionLaunched [username]\n[ExceptionMessage]CR

...
...

_notes:_
_Da discutere con i tutor la possibilità di serializzazione degli oggetti e/o utilizzo json per invio e ricezione di file tra client e server_



