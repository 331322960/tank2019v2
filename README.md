# tank2019v2

马士兵Tank项目

运行环境

`IEDA JDK11 + NETTY4.1.9 + Junit5.4`


`Config`配置说明：

敌人数量 ：需要随机方向和随机子弹自己解开注释   

`initTankCount=0`     

下面窗口大小自己依照Player类反射内容自己加到TankFrame

`gameWitdh=600 ` 

`gameHeight=480`

子弹发射策略模式

`tankFireStrategy=DefaultFireStrategy`

碰撞策略模式

`colliders=BulletTankCollider,BulletWallCollider,TankWallCollider,TankTankCollider,PlayerTankCollider`  
