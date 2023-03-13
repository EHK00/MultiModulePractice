Multi module practice

멀티 모듈 연습을 위한 프로젝트입니다.


의존 관계 그래프
clean architecture을 바탕으로 의존관계 그래프를 형성합니다.
<사진>

의존 관계 설명
domain : 앱의 비즈니스 로직과 비즈니스 로직에 필요한 model을 포함합니다.
resource id를 참조하기 위한 ResourceProvider interface는 app 모듈에서,
저장소 데이터를 참조하기 위한 repository interface는 data 모듈에서 진행합니다.

data: repository 구현을 포함합니다. usecase와 구체화된 저장소 사이의 어뎁터 역할을 수행합니다.
이 프로젝트에서는 usecase와 file 데이터 사이의 어뎁터 역할을 합니다.
로컬 저장소 참조를 위한 FileProvider interface는 app 모듈에서 구현됩니다.

feature: 기능 모듈로 사용자가 상호작용하는 화면을 담당합니다.
공통의 기능을 구현하는 common이 존재하며 common 모듈을 제외하곤 feature 모듈 간의 종속성은 불허합니다.
기능 모듈 사이의 화면 전환을 위해, feature::common에 navigator interface를 포함하며 이는 app 모듈에서 구현됩니다.

app: 클린 아키텍처 기준 가장 외부의 원을 담당하는 모듈입니다.
프로덕트의 진입점이자 안드로이드 프레임워크를 필요하는 기능들의 구현 및 의존성 주입을 담당합니다.
ResourceProvider을 구현하여 domain 모듈로,
navigator을 구현하여 feature 모듈로,
FileProvider을 구현하여 data 모듈로 주입합니다.

논의 요소
MemoRepositoryImpl에 대한 주입을 app 모듈이 아닌 data 모듈에서 진행하는건 어떤지?






