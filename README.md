# intellij-d-day-statusbar-plugin
Kotlin 과 kotlinx.date 를 이용하여 만든 간단한 IntelliJ Plugin

## Install

- `DdayStatusBarWidgetPresentation` class 의 `date` property 에 iso 형식으로 원하는 날짜를 지정한다. (예 : `1997-09-14T00:00:00Z`)
- 터미널을 열어 `./gradlew buildPlugin` 명령어를 입력한다.
- 빌드가 끝나면 `build/distributions` 폴더에 있는 zip 파일을 확인한다.
- IntelliJ 기반 IDE 에서 Plugin 설치창을 열고 Install Plugin from Disk 를 통해 아까 빌드한 zip 파일을 선택한다.
- 끗
