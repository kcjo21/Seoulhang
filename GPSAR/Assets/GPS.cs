﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;
using System.Data;
using Mono.Data.SqliteClient;
using System.IO;

public class GPS : MonoBehaviour {

    public static GPS Instance { set; get; }
    public GameObject obj;
    public double latitude;
    public double longitude;
    public string region_code;
    public string region_name;
   


    public void Start()
    {
        Instance = this;
        DontDestroyOnLoad(gameObject);
        StartCoroutine(StartLocationService());
    }


    private IEnumerator StartLocationService()
    {


        if (!Input.location.isEnabledByUser)
        {
            Debug.Log("User has not enabled GPS");
            yield break;
        }
        Input.location.Start();
        int maxWait = 20;
        while(Input.location.status == LocationServiceStatus.Initializing && maxWait >0)
        {
            yield return new WaitForSeconds(1);
            maxWait--;
        }
        if(maxWait <=0)
        {
            Debug.Log("Timed out");
            yield break;
        }
        if(Input.location.status == LocationServiceStatus.Failed)
        {
            Debug.Log("Unable to determin device location");
            yield break;
        }

        latitude = Input.location.lastData.latitude;
        longitude = Input.location.lastData.longitude;

        string region_c = "";
        string region_n = "";
        string filepath = Application.persistentDataPath + "/new_gps.db";

        if (!File.Exists(filepath))
        {
            WWW loadDB = new WWW("jar:file://" + Application.dataPath + "!/assets/" + "new_gps.db");  
            while (!loadDB.isDone) { }
            File.WriteAllBytes(filepath, loadDB.bytes);
        }

        string _constr = "URI=file:" + filepath;
        Debug.Log(_constr);
        IDbConnection _dbc;
        IDbCommand _dbcm;
        IDataReader _dbr;

        _dbc = new SqliteConnection(_constr);
        _dbc.Open();
        _dbcm = _dbc.CreateCommand();
        _dbcm.CommandText = "SELECT region_code,station_name FROM GPSDATA WHERE (abs(lat-" + latitude + "))*(abs(lat-" + latitude + "))+(abs(long-" + longitude + "))*(abs(long-" + longitude + "))<=(0.005)*(0.005)"; //현재위치에서 500m안에 있는 역
        _dbr = _dbcm.ExecuteReader();
        while (_dbr.Read())
        {
            region_c = _dbr.GetString(0);
            region_n = _dbr.GetString(1);
        }

        _dbr.NextResult();


        region_code = region_c;
        region_name = region_n;

        if (region_c == "" || region_n == "")
        {
            obj = GameObject.Find("3d");
            obj.SetActive(false);
            region_code = " 해당 역 없음";
            region_name = " 해당 역 없음";
        }

        yield break;
    }
}
