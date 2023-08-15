package com.example.a10minworkout.Database

import com.example.a10minworkout.R

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{

        val exerciseList = ArrayList<ExerciseModel>()

        val exerciseOne = ExerciseModel(1,"Warm Up", R.drawable.start,false,false)
        exerciseList.add(exerciseOne)

        val exerciseTwo = ExerciseModel(2,"Body Stretch", R.drawable.start,false,false)
        exerciseList.add(exerciseTwo)

        val exerciseThree = ExerciseModel(3,"Jumping Jacks", R.drawable.start,false,false)
        exerciseList.add(exerciseThree)

        val exerciseFour = ExerciseModel(4,"Hammer using Dumbbell", R.drawable.start,false,false)
        exerciseList.add(exerciseFour)

        val exerciseFive = ExerciseModel(5,"Punch", R.drawable.start,false,false)
        exerciseList.add(exerciseFive)

        val exerciseSix = ExerciseModel(6,"Push-ups", R.drawable.start,false,false)
        exerciseList.add(exerciseSix)

        val exerciseSeven = ExerciseModel(7,"Bench-Press", R.drawable.start,false,false)
        exerciseList.add(exerciseSeven)

        val exerciseEight = ExerciseModel(8,"Russian Twists", R.drawable.start,false,false)
        exerciseList.add(exerciseEight)

        val exerciseNine = ExerciseModel(9,"Sit-ups", R.drawable.start,false,false)
        exerciseList.add(exerciseNine)

        val exerciseTen = ExerciseModel(10,"Jumping Squats", R.drawable.start,false,false)
        exerciseList.add(exerciseTen)

        val exerciseEleven = ExerciseModel(11,"Plank", R.drawable.start,false,false)
        exerciseList.add(exerciseEleven)

        val exerciseTwelve = ExerciseModel(12,"Squats", R.drawable.start,false,false)
        exerciseList.add(exerciseTwelve)

        val exerciseThirteen = ExerciseModel(13,"Plank", R.drawable.start,false,false)
        exerciseList.add(exerciseThirteen)

        val exerciseFourteen = ExerciseModel(14,"Cobra Stretch", R.drawable.start,false,false)
        exerciseList.add(exerciseFourteen)

        val exerciseFifteen = ExerciseModel(15,"Meditate", R.drawable.start,false,false)
        exerciseList.add(exerciseFifteen)
        return exerciseList
    }
}