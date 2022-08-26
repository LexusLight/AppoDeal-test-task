# AppoDeal-test-task
## Задание 1:

Зарегистрируйтесь в www.appodeal.com и загрузите Appodeal SDK для Android или iOS.
Создайте приложение в которое нужно интегрировать Appodeal SDK. 
Приложение должно содержать активити с несколькими кнопками по центру экрана:

* Кнопка Banners: 
при нажатии на кнопку будут появляться баннеры сверху
(BANNER_TOP). На время показа других типов рекламы, необходимо скрывать
баннеры. После показа 5 баннеров необходимо скрыть этот тип рекламы

* Кнопка Interstitials: При нажатии на кнопку будут отображаться interstitial.
Показывать interstitials не чаще чем раз в минуту. Когда показ сделать невозможно,
кнопка должна быть не активной.

* Кнопка Rewarded Video: Должна быть активна только при наличии загруженного
rewarded video. За одну сессию нельзя показать больше 3х rewarded video. Когда
показ сделать невозможно, кнопка должна быть не активной.

* Кнопка Native: При нажатии на кнопку будет появляться ListView / UITableView со
встроенной нативной рекламой (не менее 3 штук)

### Результат:
![gif](https://i.imgur.com/ttq9LNQ.gif)

## Ответы на вопросы.
### Почему имеются пустые реализации калбеков?
Встроенные интерфейсы обязуют реализовывать все методы. Можно было-бы реализовать промежуточный интерфейс и вынести его, чтобы не видеть. Однако это это усложнение.
### Почему конфигурация рекламы не вынесена в отдельный файл? 
Потому что калбеки в конфигурации воздействуют на элементы активити. Если-бы я вынес конфигурацию в отдельный класс, пришлось бы линковать эти элементы. В контексте задачи это усложнение.

## Задание 2:
Подготовить деловое письмо-ответ на вопрос клиента:

Hello, team!
We got an email from google play about violations of their rules. They told us that we place
interstitial ads so that they suddenly appear when a user is focused on a task at hand (e.g.
playing a game, filling out a form, reading content) may lead to accidental clicks and often
creates a frustrating user experience(see on the screenshot below).
Could you please describe how we can fix it?
Kind regards, *Username*

### Письмо-ответ
Good day, *Username*!
We have received your request and can give you some recommendations on interstitial ads placement.
Google cares about its users and believes that ads should not appear at the unconscious moment of user's action.
Therefore, ads like interstitial should appear after user's finished action, but not before them.
An example: Ads should not appear right after starting of the application.
We advise you to embed the banner after any transition in your app. Hope it helps to fix your problem.
Best regards, *Name*
Future CEO of Appodeal
