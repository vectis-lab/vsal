/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Dmitry Degrave
 * Copyright (c) 2019 Garvan Institute of Medical Research
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package au.org.garvan.vsal.core.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CoreVariant")
public class CoreVariant implements Comparable<CoreVariant> {

    private String  c; // chromosome
    private Integer s; // start
    private String rs; // dbSNP

    private String a; // alternate
    private String r; // reference
    private String t; // type

    // cohort-wide alt allele stats
    private Float       ac; // alt allele count
    private Float       af; // alt allele freq
    private Integer   homc; // alt allele hom count
    private Integer   hetc; // alt allele het count

    // virtual cohort-wide alt allele stats
    private Float      vac; // alt allele count
    private Float      vaf; // alt allele freq
    private Integer  vhomc; // alt allele hom count
    private Integer  vhetc; // alt allele het count

    // stats tests outcomes
    private Float      hwe; // p-value for Chi-squared test for deviation from Hardy-Weinberg Equilibrium
    private Float     chi2; // Pearson's chi-squared test p-value
    private Float       or; // Pearson's chi-squared test odds ratio

    public CoreVariant() {
        // needed for JAXB
    }

    public CoreVariant(String c, Integer s, String rs, String a, String r, String t, Float ac, Float af, Integer homc, Integer hetc, Float vac, Float vaf, Integer vhomc, Integer vhetc, Float hwe, Float chi2, Float or) {
        this.c = c;
        this.s = s;
        this.rs = rs;
        this.a = a;
        this.r = r;
        this.t = t;
        this.ac = ac;
        this.af = af;
        this.homc = homc;
        this.hetc = hetc;
        this.vac = vac;
        this.vaf = vaf;
        this.vhomc = vhomc;
        this.vhetc = vhetc;
        this.hwe = hwe;
        this.chi2 = chi2;
        this.or = or;
    }

    // Chapter 30 "Object Equality", "Programming in Scala" 3rd ed
    private boolean canEqual(Object o) {
        return o instanceof CoreVariant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!this.canEqual(o)) return false;
        CoreVariant that = (CoreVariant) o;
        if (!c.equals(that.c)) return false;
        if (!s.equals(that.s)) return false;
        if (!a.equals(that.a)) return false;
        return r.equals(that.r);
    }

    @Override
    public int hashCode() {
        int result = c.hashCode();
        result = 31 * result + s.hashCode();
        result = 31 * result + a.hashCode();
        result = 31 * result + r.hashCode();
        return result;
    }

    @Override
    public int compareTo(CoreVariant other) {
        // chromosome, case sensitive
        int cmp = this.c.compareTo(other.c);
        if (cmp < 0) return -1;
        else if (cmp > 0) return 1;
        // start
        cmp = this.s.compareTo(other.s);
        if (cmp < 0) return -1;
        else if (cmp > 0) return 1;
        // ref, case sensitive
        cmp = this.r.compareTo(other.r);
        if (cmp < 0) return -1;
        else if (cmp > 0) return 1;
        // alt, case sensitive
        cmp = this.a.compareTo(other.a);
        if (cmp < 0) return -1;
        else if (cmp > 0) return 1;
        return 0;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public Integer getS() {
        return s;
    }

    public void setS(Integer s) {
        this.s = s;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public Float getAc() {
        return ac;
    }

    public void setAc(Float ac) {
        this.ac = ac;
    }

    public Float getAf() {
        return af;
    }

    public void setAf(Float af) {
        this.af = af;
    }

    public Integer getHomc() {
        return homc;
    }

    public void setHomc(Integer homc) {
        this.homc = homc;
    }

    public Integer getHetc() {
        return hetc;
    }

    public void setHetc(Integer hetc) {
        this.hetc = hetc;
    }

    public Float getVac() {
        return vac;
    }

    public void setVac(Float vac) {
        this.vac = vac;
    }

    public Float getVaf() {
        return vaf;
    }

    public void setVaf(Float vaf) {
        this.vaf = vaf;
    }

    public Integer getVhomc() {
        return vhomc;
    }

    public void setVhomc(Integer vhomc) {
        this.vhomc = vhomc;
    }

    public Integer getVhetc() {
        return vhetc;
    }

    public void setVhetc(Integer vhetc) {
        this.vhetc = vhetc;
    }

    public Float getHwe() {
        return hwe;
    }

    public void setHwe(Float hwe) {
        this.hwe = hwe;
    }

    public Float getChi2() {
        return chi2;
    }

    public void setChi2(Float chi2) {
        this.chi2 = chi2;
    }

    public Float getOr() {
        return or;
    }

    public void setOr(Float or) {
        this.or = or;
    }
}