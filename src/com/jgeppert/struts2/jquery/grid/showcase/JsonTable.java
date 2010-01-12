/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.jgeppert.struts2.jquery.grid.showcase;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.jgeppert.struts2.jquery.grid.showcase.dao.CustomersDao;
import com.jgeppert.struts2.jquery.grid.showcase.model.Customers;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value = "showcase")
public class JsonTable extends ActionSupport {

  private static final long   serialVersionUID = 5078264277068533593L;
  private static final Log    log              = LogFactory.getLog(JsonTable.class);

  //Your result List
  private List<Customers>      gridModel;
  
  //get how many rows we want to have into the grid - rowNum attribute in the grid
  private Integer             rows             = 0;

  //Get the requested page. By default grid sets this to 1.
  private Integer             page             = 0;

  // sorting order - asc or desc
  private String              sord;

  // get index row - i.e. user click to sort.
  private String              sidx;

  // Search Field
  private String              searchField;

  // The Search String
  private String              searchString;

  // he Search Operation ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc'] 
  private String              searchOper;

  // Your Total Pages
  private Integer             total            = 0;

  // All Records
  private Integer             records           = 0;


  private boolean             loadonce         = false;
  private CustomersDao customersDao = new CustomersDao();

  @Actions( {
    @Action(value = "/jsontable", results = {
      @Result(name = "success", type = "json")
    })
  })
  public String execute()
  {
    log.debug("Page " + getPage()+" Rows " + getRows() +" Sorting Order "+ getSord()+" Index Row :" + getSidx());
    log.debug("Search :" + searchField + " " + searchOper + " " + searchString);


    gridModel = customersDao.getAll();

    //Count all record (select count(*) from your_custumers)
    records = gridModel.size();

    //Calucalate until rows ware selected
    int to = (rows * page);
    
    //Calculate the first row to read
    int from = to - rows;

    // Set to = max rows
    if (to > records) to = records;


    //Calculate total Pages
    total = (int) Math.ceil((double) records / (double)rows);

    return SUCCESS;
  }

  public String getJSON()
  {
    return execute();
  }

  /**
   * @return how many rows we want to have into the grid
   */
  public Integer getRows()
  {
    return rows;
  }

  /**
   * @param rows
   *          how many rows we want to have into the grid
   */
  public void setRows(Integer rows)
  {
    this.rows = rows;
  }

  /**
   * @return current page of the query
   */
  public Integer getPage()
  {
    return page;
  }

  /**
   * @param page
   *          current page of the query
   */
  public void setPage(Integer page)
  {
    this.page = page;
  }

  /**
   * @return total pages for the query
   */
  public Integer getTotal()
  {
    return total;
  }

  /**
   * @param total
   *          total pages for the query
   */
  public void setTotal(Integer total)
  {
    this.total = total;
  }

  /**
   * @return total number of records for the query. e.g. select count(*) from
   *         table
   */
  public Integer getRecords()
  {
    return records;
  }

  /**
   * @param record
   *          total number of records for the query. e.g. select count(*) from
   *          table
   */
  public void setRecords(Integer records)
  {

    this.records = records;

    if (this.records > 0 && this.rows > 0)
    {
      this.total = (int) Math.ceil((double) this.records / (double) this.rows);
    }
    else
    {
      this.total = 0;
    }
  }

  /**
   * @return an collection that contains the actual data
   */
  public List<Customers> getGridModel()
  {
    return gridModel;
  }

  /**
   * @return sorting order
   */
  public String getSord()
  {
    return sord;
  }

  /**
   * @param sord
   *          sorting order
   */
  public void setSord(String sord)
  {
    this.sord = sord;
  }

  /**
   * @return get index row - i.e. user click to sort.
   */
  public String getSidx()
  {
    return sidx;
  }

  /**
   * @param sidx
   *          get index row - i.e. user click to sort.
   */
  public void setSidx(String sidx)
  {
    this.sidx = sidx;
  }

  public void setSearchField(String searchField)
  {
    this.searchField = searchField;
  }

  public void setSearchString(String searchString)
  {
    this.searchString = searchString;
  }

  public void setSearchOper(String searchOper)
  {
    this.searchOper = searchOper;
  }

  public void setLoadonce(boolean loadonce)
  {
    this.loadonce = loadonce;
  }
}