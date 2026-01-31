package com.roshnab.aasra.data

import org.osmdroid.util.GeoPoint

object RiverRepository {

    // 1. PUBLIC ACCESSOR
    fun getRiverPolygons(): List<List<GeoPoint>> {
        return getOriginalRiverPolygons() + getRiverJoints()
    }

    // 2. THE BARRAGES (Updated with Real Data)
    fun getBarrages(): List<Barrage> {
        return listOf(
            Barrage("Kotri", "Indus River", "15 m", GeoPoint(25.4428, 68.3151), "12,312 (Rising)", "2,177 (Rising)", "NORMAL"),
            Barrage("Sukkur", "Indus River", "57 m", GeoPoint(27.6790, 68.8462), "22,090 (Rising)", "10,590 (Rising)", "NORMAL"),
            Barrage("Guddu", "Indus River", "74 m", GeoPoint(28.4201, 69.7114), "28,776 (Steady)", "23,183 (Steady)", "NORMAL"),
            Barrage("Panjnad", "Chenab River", "100 m", GeoPoint(29.3464, 71.0202), "10,413 (Rising)", "10,413 (Rising)", "NORMAL"),
            Barrage("Taunsa", "Indus River", "131 m", GeoPoint(30.5185, 70.8367), "24,208 (Steady)", "24,208 (Steady)", "NORMAL"),
            Barrage("Chashma", "Indus River", "191 m", GeoPoint(32.4359, 71.3794), "30,228 (Falling)", "30,228 (Falling)", "NORMAL"),
            Barrage("Kalabagh", "Indus River", "211 m", GeoPoint(32.9187, 71.5219), "24,090 (Falling)", "24,090 (Falling)", "NORMAL"),
            Barrage("KABUL", "Kabul River", "287 m", GeoPoint(34.0067, 71.9770), "N/A", "11,600 (Falling)", "NORMAL"),
            Barrage("Tarbela Dam", "Indus River", "430 m", GeoPoint(34.1059, 72.7338), "17,000 (Steady)", "13,300 (Rising)", "NORMAL"),
            Barrage("Besham", "Indus River", "595 m", GeoPoint(34.9000, 72.8600), "N/A", "14,900 (Rising)", "NORMAL"),
            Barrage("Partab Bridge", "Indus River", "2093 m", GeoPoint(35.4400, 74.3700), "N/A", "11,300 (Rising)", "NORMAL"),
            Barrage("Skardu", "Indus River", "2190 m", GeoPoint(35.3380, 75.6040), "N/A", "7,300 (Falling)", "NORMAL"),
            Barrage("Islam", "Sutlej River", "131 m", GeoPoint(29.8263, 72.5485), "1,777 (Steady)", "1,777 (Steady)", "NORMAL"),
            Barrage("Sulemanki", "Sutlej River", "170 m", GeoPoint(30.3777, 73.8663), "2,640 (Steady)", "2,640 (Steady)", "NORMAL"),
            Barrage("Ganda Singh Wala", "Sutlej River", "200 m", GeoPoint(30.9954, 74.5407), "N/A", "0 (Steady)", "NORMAL"),
            Barrage("Sidhnai", "Ravi River", "141 m", GeoPoint(30.5723, 72.1582), "2,946 (Rising)", "2,946 (Rising)", "NORMAL"),
            Barrage("Balloki", "Ravi River", "192 m", GeoPoint(31.2222, 73.8596), "2,000 (Rising)", "N/A", "NORMAL"),
            Barrage("Shahdara", "Ravi River", "201 m", GeoPoint(31.6085, 74.2956), "N/A", "821 (Steady)", "NORMAL"),
            Barrage("Jassar", "Ravi River", "229 m", GeoPoint(32.0451, 74.8657), "N/A", "1,190 (Falling)", "NORMAL"),
            Barrage("Trimmu", "Chenab River", "149 m", GeoPoint(31.1448, 72.1460), "6,653 (Falling)", "6,653 (Falling)", "NORMAL"),
            Barrage("Q.Abad", "Chenab River", "204 m", GeoPoint(32.3209, 73.6851), "8,355 (Falling)", "8,355 (Falling)", "NORMAL"),
            Barrage("Khanki", "Chenab River", "213 m", GeoPoint(32.4088, 73.9685), "6,100 (Falling)", "6,100 (Falling)", "NORMAL"),
            Barrage("Marala", "Chenab River", "242 m", GeoPoint(32.6735, 74.4647), "16,058 (Rising)", "12,058 (Rising)", "NORMAL"),
            Barrage("Rasul", "Jhelum River", "211 m", GeoPoint(32.6832, 73.5186), "7,500 (Rising)", "N/A", "NORMAL"),
            Barrage("Mangla Dam", "Jhelum River", "335 m", GeoPoint(33.1507, 73.6491), "7,000 (Falling)", "10,000 (Steady)", "NORMAL")
        )
    }

    // 3. BASIN
    fun getRiverBasin(): List<GeoPoint> {
        return listOf(
            GeoPoint(35.75, 74.85), GeoPoint(35.60, 73.10), GeoPoint(35.30, 71.50),
            GeoPoint(34.90, 70.20), GeoPoint(34.20, 69.00), GeoPoint(33.40, 67.80),
            GeoPoint(32.50, 67.10), GeoPoint(31.50, 66.90), GeoPoint(30.40, 67.30),
            GeoPoint(29.40, 67.80), GeoPoint(28.60, 68.30), GeoPoint(27.90, 68.60),
            GeoPoint(26.80, 68.50), GeoPoint(25.80, 67.90), GeoPoint(24.90, 66.80),
            GeoPoint(24.20, 66.50), GeoPoint(24.10, 67.20), GeoPoint(24.50, 68.20),
            GeoPoint(25.10, 69.40), GeoPoint(26.00, 70.60), GeoPoint(27.20, 71.60),
            GeoPoint(28.40, 72.40), GeoPoint(29.80, 73.00), GeoPoint(31.00, 73.50),
            GeoPoint(32.10, 74.10), GeoPoint(33.20, 74.70), GeoPoint(34.30, 75.20),
            GeoPoint(35.20, 75.40), GeoPoint(35.75, 74.85)
        )
    }

    // 4. JOINTS
    private fun getRiverJoints(): List<List<GeoPoint>> {
        return listOf(
            listOf(GeoPoint(35.3913, 74.3626), GeoPoint(35.3976, 74.3891), GeoPoint(35.3971, 74.3798), GeoPoint(35.4004, 74.3708)),
            listOf(GeoPoint(34.8779, 72.8689), GeoPoint(34.8874, 72.8893), GeoPoint(34.8981, 72.8776), GeoPoint(34.899, 72.8568)),
            listOf(GeoPoint(30.5185, 70.8367), GeoPoint(30.5185, 70.8367), GeoPoint(30.5213, 70.8394), GeoPoint(30.535, 70.8097)),
            listOf(GeoPoint(30.3852, 73.8907), GeoPoint(30.3903, 73.8704), GeoPoint(30.3903, 73.8704), GeoPoint(30.3913, 73.8657)),
            listOf(GeoPoint(29.8116, 72.549), GeoPoint(29.8129, 72.55), GeoPoint(29.826, 72.5671), GeoPoint(29.8367, 72.5419)),
            listOf(GeoPoint(29.3898, 71.074), GeoPoint(29.4098, 71.0853), GeoPoint(29.4123, 71.082), GeoPoint(29.4493, 71.0617)),
            listOf(GeoPoint(29.3702, 71.0239), GeoPoint(29.347, 71.044), GeoPoint(29.3803, 71.1041), GeoPoint(29.3898, 71.074)),
            listOf(GeoPoint(31.621, 74.2699), GeoPoint(31.621, 74.2699), GeoPoint(31.62, 74.2713), GeoPoint(31.6281, 74.292)),
            listOf(GeoPoint(31.2327, 73.8485), GeoPoint(31.2327, 73.8485), GeoPoint(31.2312, 73.8508), GeoPoint(31.2416, 73.858)),
            listOf(GeoPoint(30.5634, 72.1555), GeoPoint(30.5634, 72.1776), GeoPoint(30.5651, 72.1566), GeoPoint(30.5649, 72.1398)),
            listOf(GeoPoint(30.6426, 71.8549), GeoPoint(30.6463, 71.8584), GeoPoint(30.6393, 71.8694), GeoPoint(30.6663, 71.8534)),
            listOf(GeoPoint(32.3143, 73.6902), GeoPoint(32.328, 73.6854), GeoPoint(32.3287, 73.6763), GeoPoint(32.3287, 73.6763)),
            listOf(GeoPoint(31.1331, 72.1834), GeoPoint(31.1381, 72.1834), GeoPoint(31.1444, 72.175), GeoPoint(31.1417, 72.1508)),
            listOf(GeoPoint(29.329, 71.0301), GeoPoint(29.3457, 71.0455), GeoPoint(29.347, 71.044), GeoPoint(29.3681, 71.0198)),
            listOf(GeoPoint(27.6714, 68.8683), GeoPoint(27.6912, 68.8536), GeoPoint(27.6912, 68.8536), GeoPoint(27.6871, 68.851)),
            listOf(GeoPoint(25.4413, 68.2949), GeoPoint(25.4413, 68.2949), GeoPoint(25.4441, 68.2995), GeoPoint(25.4637, 68.3103))
        )
    }

    // 5. ORIGINAL
    private fun getOriginalRiverPolygons(): List<List<GeoPoint>> {
        return listOf(
            listOf(GeoPoint(35.366, 75.556), GeoPoint(35.473, 75.344), GeoPoint(35.574, 75.079), GeoPoint(35.793, 74.651), GeoPoint(35.456, 74.536), GeoPoint(35.397, 74.38), GeoPoint(35.494, 74.581), GeoPoint(35.732, 74.62), GeoPoint(35.734, 74.768), GeoPoint(35.596, 75.081), GeoPoint(35.585, 75.34), GeoPoint(35.463, 75.434), GeoPoint(35.366, 75.556)),
            listOf(GeoPoint(34.93, 72.907), GeoPoint(34.962, 72.883), GeoPoint(35.048, 72.944), GeoPoint(35.122, 73.043), GeoPoint(35.258, 73.214), GeoPoint(35.323, 73.188), GeoPoint(35.432, 73.198), GeoPoint(35.486, 73.283), GeoPoint(35.526, 73.407), GeoPoint(35.54, 73.498), GeoPoint(35.552, 73.628), GeoPoint(35.526, 73.774), GeoPoint(35.496, 73.907), GeoPoint(35.458, 74.013), GeoPoint(35.441, 74.107), GeoPoint(35.439, 74.262), GeoPoint(35.4, 74.371), GeoPoint(35.406, 74.288), GeoPoint(35.386, 74.176), GeoPoint(35.409, 74.035), GeoPoint(35.47, 73.916), GeoPoint(35.492, 73.793), GeoPoint(35.517, 73.603), GeoPoint(35.507, 73.443), GeoPoint(35.482, 73.31), GeoPoint(35.408, 73.22), GeoPoint(35.302, 73.223), GeoPoint(35.203, 73.182), GeoPoint(35.105, 73.053), GeoPoint(35.037, 72.96), GeoPoint(34.969, 72.926), GeoPoint(34.93, 72.907)),
            listOf(GeoPoint(34.863, 72.918), GeoPoint(34.85, 72.993), GeoPoint(34.78, 72.95), GeoPoint(34.751, 72.843), GeoPoint(34.604, 72.801), GeoPoint(34.518, 72.825), GeoPoint(34.342, 72.869), GeoPoint(34.095, 72.759), GeoPoint(34.172, 72.787), GeoPoint(34.474, 72.838), GeoPoint(34.585, 72.793), GeoPoint(34.767, 72.825), GeoPoint(34.818, 72.963), GeoPoint(34.863, 72.918)),
            listOf(GeoPoint(34.157, 71.598), GeoPoint(34.126, 71.682), GeoPoint(34.085, 71.729), GeoPoint(34.055, 71.782), GeoPoint(34.011, 71.852), GeoPoint(34.031, 71.911), GeoPoint(34.002, 71.973), GeoPoint(34.002, 72.077), GeoPoint(33.969, 72.193), GeoPoint(33.928, 72.248), GeoPoint(33.968, 72.225), GeoPoint(34.012, 72.127), GeoPoint(34.028, 72.034), GeoPoint(34.023, 71.972), GeoPoint(34.051, 71.908), GeoPoint(34.07, 71.79), GeoPoint(34.109, 71.72), GeoPoint(34.16, 71.624), GeoPoint(34.168, 71.596)),
            listOf(GeoPoint(32.419, 71.36), GeoPoint(32.137, 71.139), GeoPoint(31.84, 70.919), GeoPoint(31.659, 70.892), GeoPoint(31.408, 70.767), GeoPoint(31.272, 70.747), GeoPoint(31.114, 70.786), GeoPoint(30.945, 70.803), GeoPoint(30.703, 70.787), GeoPoint(30.535, 70.81), GeoPoint(30.592, 70.829), GeoPoint(30.81, 70.87), GeoPoint(31.173, 70.819), GeoPoint(31.491, 70.824), GeoPoint(31.723, 70.976), GeoPoint(31.962, 71.062), GeoPoint(32.11, 71.223), GeoPoint(32.27, 71.337), GeoPoint(32.405, 71.402), GeoPoint(32.419, 71.36)),
            listOf(GeoPoint(30.519, 70.837), GeoPoint(30.143, 70.795), GeoPoint(29.52, 70.687), GeoPoint(29.085, 70.735), GeoPoint(29.319, 70.735), GeoPoint(29.674, 70.784), GeoPoint(29.915, 70.849), GeoPoint(30.162, 70.854), GeoPoint(30.391, 70.835), GeoPoint(30.528, 70.871), GeoPoint(30.519, 70.837)),
            listOf(GeoPoint(30.363, 73.873), GeoPoint(30.444, 73.938), GeoPoint(30.53, 74.023), GeoPoint(30.62, 74.115), GeoPoint(30.709, 74.205), GeoPoint(30.807, 74.28), GeoPoint(30.903, 74.373), GeoPoint(30.973, 74.483), GeoPoint(30.948, 74.521), GeoPoint(30.88, 74.418), GeoPoint(30.821, 74.357), GeoPoint(30.707, 74.297), GeoPoint(30.645, 74.185), GeoPoint(30.549, 74.1), GeoPoint(30.455, 74.009), GeoPoint(30.369, 73.921), GeoPoint(30.345, 73.884)),
            listOf(GeoPoint(30.39, 73.87), GeoPoint(30.362, 73.724), GeoPoint(30.299, 73.525), GeoPoint(30.22, 73.337), GeoPoint(30.11, 73.222), GeoPoint(30.038, 73.077), GeoPoint(29.994, 72.862), GeoPoint(29.905, 72.723), GeoPoint(29.837, 72.538), GeoPoint(29.867, 72.67), GeoPoint(29.983, 72.91), GeoPoint(30.056, 73.223), GeoPoint(30.201, 73.372), GeoPoint(30.25, 73.504), GeoPoint(30.303, 73.675), GeoPoint(30.344, 73.794), GeoPoint(30.39, 73.87)),
            listOf(GeoPoint(29.837, 72.542), GeoPoint(29.847, 72.382), GeoPoint(29.783, 72.277), GeoPoint(29.71, 72.178), GeoPoint(29.602, 72.118), GeoPoint(29.541, 71.988), GeoPoint(29.491, 71.882), GeoPoint(29.472, 71.754), GeoPoint(29.423, 71.601), GeoPoint(29.396, 71.386), GeoPoint(29.418, 71.206), GeoPoint(29.39, 71.074), GeoPoint(29.388, 71.244), GeoPoint(29.345, 71.487), GeoPoint(29.451, 71.743), GeoPoint(29.465, 71.913), GeoPoint(29.531, 72.062), GeoPoint(29.689, 72.197), GeoPoint(29.825, 72.353), GeoPoint(29.816, 72.509), GeoPoint(29.837, 72.542)),
            listOf(GeoPoint(32.125, 75.195), GeoPoint(32.101, 75.15), GeoPoint(32.094, 75.105), GeoPoint(32.086, 75.057), GeoPoint(32.068, 74.993), GeoPoint(32.034, 74.952), GeoPoint(32.04, 74.856), GeoPoint(32.002, 74.821), GeoPoint(31.964, 74.809), GeoPoint(31.953, 74.746), GeoPoint(31.937, 74.669), GeoPoint(31.889, 74.607), GeoPoint(31.845, 74.552), GeoPoint(31.77, 74.522), GeoPoint(31.713, 74.478), GeoPoint(31.676, 74.386), GeoPoint(31.653, 74.334), GeoPoint(31.611, 74.293), GeoPoint(31.645, 74.346), GeoPoint(31.666, 74.402), GeoPoint(31.696, 74.446), GeoPoint(31.7, 74.501), GeoPoint(31.751, 74.535), GeoPoint(31.802, 74.556), GeoPoint(31.875, 74.585), GeoPoint(31.911, 74.657), GeoPoint(31.949, 74.713), GeoPoint(31.93, 74.789), GeoPoint(31.969, 74.846), GeoPoint(32.007, 74.857), GeoPoint(32.026, 74.888), GeoPoint(32.017, 74.95), GeoPoint(32.062, 75.007), GeoPoint(32.068, 75.063), GeoPoint(32.075, 75.116), GeoPoint(32.089, 75.168), GeoPoint(32.125, 75.195)),
            listOf(GeoPoint(31.621, 74.27), GeoPoint(31.492, 74.172), GeoPoint(31.413, 74.103), GeoPoint(31.369, 74.031), GeoPoint(31.329, 73.971), GeoPoint(31.304, 73.894), GeoPoint(31.257, 73.881), GeoPoint(31.215, 73.875), GeoPoint(31.261, 73.926), GeoPoint(31.309, 73.984), GeoPoint(31.391, 74.071), GeoPoint(31.455, 74.175), GeoPoint(31.544, 74.249), GeoPoint(31.621, 74.294), GeoPoint(31.621, 74.27)),
            listOf(GeoPoint(31.233, 73.848), GeoPoint(31.226, 73.729), GeoPoint(31.155, 73.651), GeoPoint(31.127, 73.471), GeoPoint(31.047, 73.372), GeoPoint(30.983, 73.233), GeoPoint(30.903, 73.187), GeoPoint(30.844, 73.087), GeoPoint(30.784, 72.984), GeoPoint(30.719, 72.878), GeoPoint(30.673, 72.728), GeoPoint(30.576, 72.634), GeoPoint(30.593, 72.428), GeoPoint(30.561, 72.246), GeoPoint(30.563, 72.178), GeoPoint(30.534, 72.284), GeoPoint(30.57, 72.434), GeoPoint(30.56, 72.617), GeoPoint(30.59, 72.742), GeoPoint(30.682, 72.886), GeoPoint(30.742, 72.989), GeoPoint(30.791, 73.106), GeoPoint(30.877, 73.227), GeoPoint(30.985, 73.296), GeoPoint(31.098, 73.471), GeoPoint(31.138, 73.674), GeoPoint(31.211, 73.764), GeoPoint(31.233, 73.848)),
            listOf(GeoPoint(30.582, 72.159), GeoPoint(30.61, 72.034), GeoPoint(30.629, 71.945), GeoPoint(30.639, 71.869), GeoPoint(30.603, 71.867), GeoPoint(30.618, 71.922), GeoPoint(30.596, 72.056), GeoPoint(30.565, 72.14), GeoPoint(30.582, 72.159)),
            listOf(GeoPoint(32.416, 73.964), GeoPoint(32.407, 73.887), GeoPoint(32.389, 73.848), GeoPoint(32.364, 73.749), GeoPoint(32.328, 73.685), GeoPoint(32.307, 73.716), GeoPoint(32.34, 73.781), GeoPoint(32.361, 73.857), GeoPoint(32.381, 73.931), GeoPoint(32.416, 73.964)),
            listOf(GeoPoint(32.329, 73.676), GeoPoint(32.308, 73.563), GeoPoint(32.245, 73.461), GeoPoint(32.175, 73.355), GeoPoint(32.086, 73.242), GeoPoint(31.993, 73.163), GeoPoint(31.918, 73.116), GeoPoint(31.844, 73.049), GeoPoint(31.798, 72.967), GeoPoint(31.729, 72.905), GeoPoint(31.696, 72.778), GeoPoint(31.704, 72.706), GeoPoint(31.697, 72.628), GeoPoint(31.626, 72.565), GeoPoint(31.551, 72.455), GeoPoint(31.512, 72.396), GeoPoint(31.482, 72.313), GeoPoint(31.421, 72.29), GeoPoint(31.384, 72.249), GeoPoint(31.339, 72.252), GeoPoint(31.3, 72.232), GeoPoint(31.263, 72.222), GeoPoint(31.235, 72.212), GeoPoint(31.243, 72.209), GeoPoint(31.199, 72.19), GeoPoint(31.151, 72.172), GeoPoint(31.164, 72.207), GeoPoint(31.218, 72.231), GeoPoint(31.275, 72.26), GeoPoint(31.347, 72.271), GeoPoint(31.398, 72.308), GeoPoint(31.455, 72.322), GeoPoint(31.472, 72.385), GeoPoint(31.521, 72.459), GeoPoint(31.568, 72.55), GeoPoint(31.646, 72.615), GeoPoint(31.674, 72.662), GeoPoint(31.663, 72.729), GeoPoint(31.657, 72.79), GeoPoint(31.677, 72.859), GeoPoint(31.707, 72.917), GeoPoint(31.734, 72.964), GeoPoint(31.777, 73.007), GeoPoint(31.82, 73.073), GeoPoint(31.882, 73.137), GeoPoint(31.951, 73.171), GeoPoint(32.015, 73.222), GeoPoint(32.067, 73.262), GeoPoint(32.104, 73.294), GeoPoint(32.13, 73.354), GeoPoint(32.158, 73.382), GeoPoint(32.202, 73.424), GeoPoint(32.221, 73.467), GeoPoint(32.246, 73.52), GeoPoint(32.262, 73.581), GeoPoint(32.281, 73.609), GeoPoint(32.31, 73.652), GeoPoint(32.329, 73.676)),
            listOf(GeoPoint(31.082, 72.11), GeoPoint(30.893, 71.959), GeoPoint(30.625, 71.754), GeoPoint(30.419, 71.491), GeoPoint(30.207, 71.341), GeoPoint(30.022, 71.195), GeoPoint(29.753, 71.142), GeoPoint(29.459, 70.994), GeoPoint(29.369, 71.072), GeoPoint(29.543, 71.076), GeoPoint(29.786, 71.19), GeoPoint(30.053, 71.305), GeoPoint(30.296, 71.443), GeoPoint(30.529, 71.65), GeoPoint(30.646, 71.858), GeoPoint(30.876, 71.984), GeoPoint(31.034, 72.117), GeoPoint(31.082, 72.11)),
            listOf(GeoPoint(29.37, 71.024), GeoPoint(29.273, 70.896), GeoPoint(29.163, 70.777), GeoPoint(29.115, 70.778), GeoPoint(29.206, 70.901), GeoPoint(29.295, 70.994), GeoPoint(29.37, 71.024)),
            listOf(GeoPoint(33.131, 73.632), GeoPoint(33.077, 73.648), GeoPoint(33.058, 73.739), GeoPoint(32.928, 73.737), GeoPoint(32.829, 73.614), GeoPoint(32.708, 73.533), GeoPoint(32.701, 73.564), GeoPoint(32.796, 73.622), GeoPoint(32.858, 73.69), GeoPoint(32.936, 73.77), GeoPoint(32.996, 73.805), GeoPoint(33.09, 73.712), GeoPoint(33.117, 73.643), GeoPoint(33.131, 73.632)),
            listOf(GeoPoint(28.428, 69.7), GeoPoint(28.32, 69.566), GeoPoint(28.129, 69.326), GeoPoint(28.081, 69.228), GeoPoint(27.998, 69.031), GeoPoint(27.901, 68.917), GeoPoint(27.687, 68.851), GeoPoint(27.86, 68.934), GeoPoint(27.899, 69.063), GeoPoint(28.054, 69.121), GeoPoint(28.067, 69.286), GeoPoint(28.101, 69.352), GeoPoint(28.293, 69.586), GeoPoint(28.373, 69.679), GeoPoint(28.428, 69.7)),
            listOf(GeoPoint(27.691, 68.854), GeoPoint(27.716, 68.734), GeoPoint(27.796, 68.662), GeoPoint(27.708, 68.553), GeoPoint(27.702, 68.441), GeoPoint(27.622, 68.4), GeoPoint(27.53, 68.298), GeoPoint(27.397, 68.286), GeoPoint(27.27, 68.13), GeoPoint(27.158, 68.061), GeoPoint(27.0, 68.009), GeoPoint(26.874, 67.9), GeoPoint(26.691, 67.872), GeoPoint(26.56, 67.824), GeoPoint(26.396, 67.869), GeoPoint(26.235, 67.93), GeoPoint(26.13, 68.071), GeoPoint(25.982, 68.212), GeoPoint(25.875, 68.307), GeoPoint(25.718, 68.327), GeoPoint(25.619, 68.399), GeoPoint(25.495, 68.32), GeoPoint(25.487, 68.345), GeoPoint(25.631, 68.448), GeoPoint(25.72, 68.355), GeoPoint(25.854, 68.377), GeoPoint(26.033, 68.209), GeoPoint(26.199, 68.045), GeoPoint(26.329, 67.933), GeoPoint(26.57, 67.926), GeoPoint(26.805, 67.93), GeoPoint(26.976, 68.056), GeoPoint(27.17, 68.217), GeoPoint(27.34, 68.212), GeoPoint(27.486, 68.326), GeoPoint(27.667, 68.451), GeoPoint(27.745, 68.636), GeoPoint(27.663, 68.738), GeoPoint(27.691, 68.854)),
            listOf(GeoPoint(25.441, 68.295), GeoPoint(25.229, 68.259), GeoPoint(24.996, 68.131), GeoPoint(24.8, 68.009), GeoPoint(24.592, 67.959), GeoPoint(24.444, 67.871), GeoPoint(24.309, 67.771), GeoPoint(24.144, 67.659), GeoPoint(24.041, 67.435), GeoPoint(24.029, 67.566), GeoPoint(24.173, 67.76), GeoPoint(24.302, 67.859), GeoPoint(24.398, 67.969), GeoPoint(24.59, 68.026), GeoPoint(24.739, 68.057), GeoPoint(24.898, 68.145), GeoPoint(24.984, 68.293), GeoPoint(25.148, 68.348), GeoPoint(25.32, 68.38), GeoPoint(25.441, 68.295))
        )
    }
}