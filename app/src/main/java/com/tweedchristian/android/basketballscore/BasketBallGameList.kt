package com.tweedchristian.android.basketballscore

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import java.util.*

private const val TAG = "GameListViewModel"

class BasketBallGameListViewModel : ViewModel() {
    private val basketBallGames = mutableListOf<Game>()
    private val gameRepository = GameRepository.get()
    val publicGames = gameRepository.getGames()

    val games: List<Game>
        get() = this.basketBallGames

    init {
        for(i in 0 until 100) {
            val game = Game()
            basketBallGames += game
        }
    }

//    val rows = listOf(
//        "bf572937-763d-48e0-a03f-798b70f30c99|Team 6KxsEjkV|Team BrFmIV7W|45|48|1599618577627",
//        "6a3b1736-5d6a-489c-adb5-e2b7066132fd|Team fgRNltd9|Team PAlgKHt0|96|62|1599618577640",
//        "1156ddc7-9bbe-4896-a0bc-ac5efcb46246|Team JnmqT4Xl|Team HU5jaqro|95|79|1599618577641",
//        "70c78b10-6f50-4aac-9b4a-6f40193c04a7|Team nxCMz24f|Team 3eK0OoRl|59|33|1599618577641",
//        "9c0a64fb-52fb-40a7-8f8d-d08e685d673d|Team SfWE9uve|Team UeAqIOe3|46|47|1599618577641",
//        "7e874b1c-7b91-4a7c-81aa-16a6e72af61b|Team 77DcK1rA|Team HyWSli3Z|66|33|1599618577642",
//        "7cb232c3-1f8d-454d-aee5-befcd6922c5b|Team e3zaAyVm|Team 5u6m6ApY|72|94|1599618577642",
//        "afdce9be-55c6-4f7f-9735-5a0ff79e1584|Team HeYRETO6|Team 54dNK7ZT|29|76|1599618577643",
//        "4bacb450-4878-4fed-bd34-cdb9e26b9dfb|Team IofZaHpQ|Team iOjLc7ky|28|84|1599618577643",
//        "d2bacd2f-b380-456a-9bdf-1e2e4c20b94a|Team 1rmBRggJ|Team iaazfHAx|93|19|1599618577644",
//        "96699492-2f42-41b7-a011-cb298a7fd88b|Team P9rGX4Kk|Team ahtLAyHE|77|39|1599618577645",
//        "2eb7dbe4-d0d2-4eb1-8c02-43ebcf36c18e|Team NU02TyYU|Team sQnHEur8|60|73|1599618577646",
//        "bbdf86fc-a4ff-4a97-ab34-3b1d33f3f53b|Team cozliHQr|Team d14MxpFE|79|67|1599618577646",
//        "8c8ee753-4961-4803-aac0-c5cc8f01ac54|Team Wu0uVdmw|Team TKA12O3L|66|63|1599618577648",
//        "1a23803e-8029-4a78-94d1-e5432c06571c|Team hxUgZm5X|Team uZVPYIP2|8|5|1599618577648",
//        "57e7ff31-84f6-4208-94e6-533bac5b8318|Team eV19iRiW|Team JoV0Zevh|51|18|1599618577649",
//        "f9523b4c-b56b-4fbc-b38c-245a2062a137|Team GicvFFWL|Team KkLwIzcV|89|63|1599618577650",
//        "1b570ecd-953a-4ad3-aa02-604612a463d6|Team LvYP3sYv|Team JfMbAItn|52|27|1599618577651",
//        "56a90b47-a238-447e-9cc4-0e149b609e0b|Team ag4ramqg|Team E2z29pz7|7|49|1599618577651",
//        "653e6cf0-0af4-4a9a-a78f-dc95e2b590a3|Team SGUPtDRL|Team TSPEexTY|78|74|1599618577652",
//        "bdc0bc2a-6657-475b-947a-77476783cc53|Team AYTCzt4j|Team 9bMHNW09|16|47|1599618577652",
//        "be4975ad-2620-4a73-840a-0f0f9dc0455d|Team OdCQxHhY|Team 5TfRoQWR|79|92|1599618577653",
//        "295c7820-2737-460f-8a80-668bfbb84207|Team gercvy8f|Team sti0DVcO|42|35|1599618577653",
//        "63402ce0-98fb-4a1b-a732-1d5dcd27093c|Team s2BHwN3W|Team 6OKMdeVo|45|94|1599618577654",
//        "3f235c58-b0f9-4d5d-9ffa-c1d733edc340|Team atco7iaN|Team maO6bu40|91|34|1599618577654",
//        "96d41458-bee0-4818-812c-8a9db12c8256|Team qd8laje1|Team ayK3UZbO|30|89|1599618577654",
//        "517aa676-a0f6-42db-a15d-db880c536b11|Team BfhmhlMA|Team JTwKgSGQ|53|56|1599618577655",
//        "09ebd906-cf37-4ef9-a128-14208baead48|Team N4ix77wJ|Team KjayVGVZ|17|80|1599618577656",
//        "f52a79c8-9de5-4dc7-a7ac-51dd153d2d93|Team 0Kr8XkGo|Team NDvkMU7O|45|45|1599618577656",
//        "302717c2-bba7-43c5-bcd1-f3b18079c893|Team nj5OV4SC|Team umYk1xYi|47|78|1599618577657",
//        "dfd2227b-ca1b-4cde-8aa4-c9a2d23ed8f5|Team eXhx448p|Team b9DcDp26|43|98|1599618577658",
//        "d3902925-b20c-47fd-ab6b-ec1751b41e81|Team B0xNWxFQ|Team PSc6L1Al|77|0|1599618577658",
//        "7f9ba58f-0e66-4e62-9a70-2e413dbf36e9|Team rg3pIncQ|Team 3g2vw0S6|11|61|1599618577659",
//        "e6b67297-d21d-4da3-bc6b-6b7dcf97f04a|Team DPApt4fF|Team 0dVeC2D5|71|74|1599618577659",
//        "7069cc0a-5a82-4ada-889f-c43701364513|Team TOs7uBIK|Team 8vUg8iH7|19|65|1599618577660",
//        "a1bfcad7-bc74-4ce7-88c8-95e6572fee87|Team hXfqbLaG|Team cqBWaYLl|67|39|1599618577660",
//        "d0ec5113-e3bb-4cea-aaf5-d5f2cabc2a8b|Team Yl4VJbXP|Team uh3POJtS|50|51|1599618577661",
//        "5b0f646a-7b38-420f-95d9-43a498e0c8f5|Team PeRaQ2by|Team PIiqzSWx|42|74|1599618577662",
//        "731de8c8-e284-4016-a7f8-fa4eeb3dd81a|Team N4By30eo|Team XyXf3ATo|45|18|1599618577662",
//        "0f79dbbf-e4d0-4d1c-803a-919ee112a66e|Team KzMJYiSJ|Team A8ybBxu2|96|73|1599618577662",
//        "cbf04442-b1c2-4356-8fff-a197c7bba4c2|Team PpRrMWXV|Team FeCVGrdu|11|74|1599618577663",
//        "4dadd0cd-6a67-4140-843e-8e55a9fde930|Team Ocy30EW5|Team dO6tMB8i|11|30|1599618577664",
//        "100e3e99-9460-485c-b03d-c6ecc86bb77f|Team t97kaOng|Team W3p8AG34|37|0|1599618577664",
//        "2a60e03c-f23c-4a06-a00e-87d52527bad1|Team FimcJzhO|Team SgsGWr1V|15|64|1599618577664",
//        "c5f1143b-7e8f-4ea6-b713-00835eb6e6ea|Team chZ9sWuQ|Team RW2OR9j2|84|57|1599618577665",
//        "55695020-2fb4-4075-b00d-b7668abd5921|Team sZp9Yyau|Team Mh0m9YWz|9|52|1599618577665",
//        "21df5ccd-ded2-434a-8ef4-cfd4f9bf647a|Team h2sCDZtZ|Team OmOmI3lK|71|13|1599618577665",
//        "9efa41e0-8def-4a77-a931-54724c81f211|Team 1DozCOcn|Team ZQrliVNx|70|21|1599618577667",
//        "e82a77b1-2f67-4123-8781-21a28de75b5e|Team ZZz7Q1nu|Team xTXQVwpp|85|79|1599618577667",
//        "910add0d-8294-40f2-b128-679a6d232477|Team vCEcDA3w|Team SAe4MiN4|63|50|1599618577668",
//        "74aaa2fc-daa7-45e2-82e8-d343eca21193|Team usJtK3jE|Team JURNGDWJ|77|59|1599618577668",
//        "9411cb43-05c4-4a85-a81a-808a4c5ed56d|Team VWPAOJni|Team j4IdkibN|42|95|1599618577668",
//        "2adaae46-0f3a-42e2-a904-210a754c824e|Team WdW1EoTH|Team tkXTJHsM|76|44|1599618577669",
//        "263b0abe-f60b-4021-b1cc-153822d15707|Team yoGxcISb|Team phjtlXJO|61|47|1599618577669",
//        "059714eb-c623-495f-9a27-c2f38db902f5|Team Tk2ki6jE|Team iGwNuXd0|33|89|1599618577670",
//        "f094e531-d8fe-41ae-9b40-7296db4074a4|Team hEtDlMwo|Team 4mdubjwO|23|61|1599618577670",
//        "40b0c166-d5b9-4295-9564-1a1660eceefa|Team sMYhi5c5|Team DQlbRCUn|7|78|1599618577670",
//        "bb96b422-5506-43ea-9437-81be366205a5|Team v1fnadAb|Team xTzmMvM5|48|90|1599618577670",
//        "a1c7f4e9-8ed8-4274-9d1b-da9de2bd1730|Team U0vhCtn7|Team vsT5bIGS|11|12|1599618577670",
//        "0e5a0e7f-305c-4ebd-b522-559708c7b5e1|Team PL9X4v2X|Team 9lySi1LF|21|26|1599618577671",
//        "45181cba-cf0d-41be-929d-ac191d9805f4|Team eDehjIqI|Team WzzdoBiA|9|63|1599618577671",
//        "503db67a-ce9d-4e87-ac11-a0a9bdefe094|Team tc2nW7HR|Team ukr2VLFH|53|12|1599618577671",
//        "b47a7240-e7f2-4c13-98f1-5ee4f52bbf92|Team 9eP47YkW|Team in57bev4|98|84|1599618577671",
//        "52d7186d-6bbd-4169-bfa9-2d96d08947eb|Team rgKwuR6t|Team Evepty2R|32|65|1599618577671",
//        "62aaf15a-ce19-4821-83e2-b89a3e2cfdea|Team ZzIIr0ep|Team wDiWoHQv|61|71|1599618577671",
//        "6eedf798-e151-4ccc-8af7-1e1dacb45e56|Team rljvwX4n|Team duGXZwAS|65|3|1599618577671",
//        "33254bc7-d2ce-4eba-acb1-71927636424d|Team UaFnOu9v|Team k2RR91sg|47|27|1599618577672",
//        "6e3b4b71-e184-4e88-a8ee-257919a91a4f|Team h1hISsdy|Team mdRT0NEt|20|76|1599618577672",
//        "cfa29db8-1487-4769-9278-efb1d735b0fc|Team zkWELPRR|Team ao8KBPL6|53|80|1599618577672",
//        "c7afcdde-22f0-4813-8e3d-5e15a311a00f|Team nabfsrqX|Team vwvAtZLG|14|34|1599618577672",
//        "44f5b5bb-257f-40ee-9bae-f9489552b5ee|Team QQvz6MMv|Team IUi3Hjn3|36|45|1599618577673",
//        "0df5198b-8ade-465b-b01c-4e7883d096a6|Team CPil7KFH|Team 4YpyN3pQ|85|58|1599618577673",
//        "86724502-3209-4873-85d4-c17a9c739335|Team I0ktDTEA|Team GztLjjuv|80|28|1599618577673",
//        "e618d0d3-eb1c-450e-b068-3f6ba0f745f1|Team B3lQZW47|Team w80SiJZH|28|69|1599618577673",
//        "a837a872-2394-424f-ac8f-6e044d65bfcc|Team kL1LE0YE|Team D0HQiQcF|31|92|1599618577674",
//        "77ebaadb-5f40-4754-bdfe-1fa0d70db29a|Team cIFOg5xS|Team VxEpLNKw|68|31|1599618577674",
//        "f35b6f29-1253-4d45-b667-d02fdd096253|Team AYOqlNXE|Team r2SdzViy|40|39|1599618577674",
//        "14a50c4a-1ff9-41a1-9044-d845e882cc83|Team U47Yidxn|Team l99OyKBK|74|95|1599618577675",
//        "d6e8f54e-84a4-440a-a122-67b72806e2ba|Team EbxvvN1z|Team tvt6jF3J|19|12|1599618577677",
//        "6492b4db-d985-47b1-911e-564ec4bf9c82|Team a1cmEhDX|Team ZV7Lm8M5|23|72|1599618577678",
//        "cc607e04-752a-4b3f-bb28-aa40a10383f2|Team EniFFb6d|Team sz8bNuBK|60|83|1599618577678",
//        "c8164bb7-8d60-4084-870d-0385216c341e|Team pNOUYx8X|Team n0t6ooAr|94|82|1599618577678",
//        "c9231ea8-81d1-44c2-856b-294b7d181888|Team 5TVV1nCs|Team 7YfowZU4|93|56|1599618577678",
//        "fa347a4b-3b56-42fc-823f-2ca47a4ec250|Team vwzJppMF|Team OhtCTaBB|88|65|1599618577679",
//        "89a72d91-f892-4458-adf5-1c5b5e81f03c|Team qgaKmR4l|Team KIA5riPt|11|89|1599618577679",
//        "ecce1f57-564f-44bd-8163-fe21a752e53a|Team nm3Lt17M|Team J90QAoAF|43|11|1599618577679",
//        "80994cd4-7886-4b0a-9b93-841ae3ec4dd0|Team OgYFXRO2|Team y2IHasdT|96|61|1599618577680",
//        "a0e473a1-75f2-4059-9cb5-1cd048c04ebb|Team PUlETPN2|Team raDnILdJ|99|84|1599618577680",
//        "54ca3e45-9b59-4239-af53-a76f9e90fb50|Team GAJRztZP|Team a9mtSIuZ|18|7|1599618577680",
//        "2dca6ea5-bc91-4982-806d-d4ae0833c310|Team WIR17QUc|Team UwgWv9zg|81|8|1599618577680",
//        "90c21728-cf9b-460a-b0ae-9f81dcd156df|Team mqu2oWYw|Team xs2NpsvA|3|27|1599618577681",
//        "16603a21-0541-4a8d-9d69-0056f35aee38|Team OOsMp2s9|Team KI4egA11|55|61|1599618577681",
//        "17fb8e0d-c97d-4d59-85f8-fe51e8f5c9d2|Team FiWzZJR8|Team U1ERotR0|13|5|1599618577681",
//        "718c28fc-55c5-45d6-a082-15ac31c97b5b|Team o6h3C48U|Team pBnOWbMg|19|3|1599618577681",
//        "e839313e-ac9f-49d9-9ca8-c40b7eb26cc0|Team CGDg5aiO|Team EUneIgZ0|47|16|1599618577681",
//        "d1c1ce08-2740-47e8-89ea-7cd034339127|Team Po0fnMcP|Team 9MVF8EKv|92|29|1599618577682",
//        "9ac44abe-b086-493e-82bf-d8135a945fda|Team gFr95T8m|Team RBPu9GPa|38|46|1599618577682",
//        "9997d4f8-2fd8-44f2-a764-f89e49636022|Team RrJaGbAA|Team mA1FSIw3|88|34|1599618577682",
//        "5e148f0f-cfa7-4ee5-a608-345b9bce98da|Team 6cmr6GWl|Team KeyBCKEK|6|70|1599618577682",
//        "3d50a644-0b29-4510-8528-b1950f2e39e1|Team BR8nTYoO|Team TeMEFNkt|39|77|1599618577682",
//    )
//    for(row in rows){
//        addRow(row)
//    }
//    private fun addRow(dataString: String) {
//        val splits = dataString.split("|")
//        val uuid = UUID.fromString(splits[0])
//        val teamOneName = splits[1]
//        val teamTwoName = splits[2]
//        val teamOnePoints = splits[3].toInt()
//        val teamTwoPoints = splits[4].toInt()
//        val milliseconds = splits[5].toLong()
//        val game = Game(uuid, teamOneName, teamTwoName, teamOnePoints, teamTwoPoints, Date(milliseconds))
////        gameRepository.addGame(game)
//    }
}